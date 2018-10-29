/**
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */
import { workspace, commands, window, ViewColumn, ExtensionContext, TextEditor, WebviewPanel, TextDocumentChangeEvent } from 'vscode';
import { ExtendedLangClient } from '../core/extended-language-client';
import * as _ from 'lodash';
import { apiEditorRender } from './renderer';
import { BallerinaExtension } from '../core';
import { WebViewRPCHandler } from '../utils';

const DEBOUNCE_WAIT = 500;

let oasEditorPanel: WebviewPanel | undefined;
let activeEditor: TextEditor | undefined;

function showAPIEditorPanel(context: ExtensionContext, langClient: ExtendedLangClient) : any {
    workspace.onDidChangeTextDocument(
        _.debounce((e: TextDocumentChangeEvent) => {

    }, DEBOUNCE_WAIT));

    window.onDidChangeActiveTextEditor(
        (activatedEditor: TextEditor | undefined) => {

    });
    
    let selectedService : string;
    const editor = window.activeTextEditor;

    // TODO : proper handler if not the active editor
    if (!editor) {
        return "";
    }
    activeEditor = editor;

    langClient.getServiceListForActiveFile(activeEditor.document.uri).then(resp => {
        if (resp.services && resp.services.length > 1) {
            window.showQuickPick(resp.services).then(service => {
                if (service && activeEditor ) {
                    selectedService = service
                    let renderHtml = apiEditorRender(context, 
                        langClient, editor.document.uri, service);
                    createAPIEditorPanel(selectedService, renderHtml, langClient, context);
                }
            });
        } else {
            selectedService = resp.services[0];
            let renderHtml = apiEditorRender(context, 
                langClient, editor.document.uri, selectedService);
            createAPIEditorPanel(selectedService, renderHtml, langClient, context);
        }
    });
}

function createAPIEditorPanel(selectedService: string, renderHtml: string,
    langClient: ExtendedLangClient, context: ExtensionContext) : any {

    if (!oasEditorPanel) {
        oasEditorPanel = window.createWebviewPanel(
            'ballerinaOASEditor',
            'Ballerina API Editor - ' + selectedService,
            { viewColumn: ViewColumn.Two, preserveFocus: true } ,
            {
                enableScripts: true,
                retainContextWhenHidden: true,
            }
        )
    }

    oasEditorPanel.webview.html = renderHtml;

    WebViewRPCHandler.create([{
        methodName: 'getSwaggerDef',
        handler: (args: any[]) => {
            return langClient.getBallerinaOASDef(args[0], args[1]);
        }
    },{
        methodName: 'triggerSwaggerDefChange',
        handler: (args: any[]) => {
            return langClient.triggerSwaggerDefChange(args[0], args[1]);
        }
    }], oasEditorPanel.webview);

    oasEditorPanel.webview.onDidReceiveMessage(message => {
        switch (message.command) {
            case 'oasASTModified' :
                return;
        }
    }, undefined, context.subscriptions);

    oasEditorPanel.onDidDispose(() => {
        oasEditorPanel = undefined;
    });
}

export function activate(ballerinaExtInstance: BallerinaExtension) {
    let context = <ExtensionContext> ballerinaExtInstance.context;
    let langClient = <ExtendedLangClient> ballerinaExtInstance.langClient;
    const showAPIRenderer = commands.registerCommand('ballerina.showAPIEditor', () => {
        ballerinaExtInstance.onReady()
        .then(() => {
            const { experimental } = langClient.initializeResult!.capabilities;
            const serverProvidesAPIEditorFeature = experimental && experimental.apiEditorProvider;

            if (!serverProvidesAPIEditorFeature) {
                ballerinaExtInstance.showMessageServerMissingCapability();
                return;
            }

            showAPIEditorPanel(context, langClient)
        })
		.catch((e) => {
			if (!ballerinaExtInstance.isValidBallerinaHome()) {
				ballerinaExtInstance.showMessageInvalidBallerinaHome();
			} else {
				ballerinaExtInstance.showPluginActivationError();
			}
		});
    });
    
    context.subscriptions.push(showAPIRenderer);
}
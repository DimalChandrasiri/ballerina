import { ExtendedLangClient } from '../lang-client';
import { Uri, ExtensionContext } from 'vscode';
import { getLibraryWebViewContent } from '../utils';

export function apiEditorRender(context: ExtensionContext, langClient: ExtendedLangClient, docUri: Uri, selectedService: string, retries: number = 1) : string {
    const body = `
        <div class='api-container'>
            <div class='message'></div>
            <div class='api-visualizer' id='api-visualizer'></div>
        </div>
    `;

    const styles = `
        body {
            background-color: #1e1e1e;
            color: #fff;
        }
    `;

    const script = `
        let docUri = ${JSON.stringify(docUri.toString())};
        let updatedJSON = '';
        let selectedService = ${JSON.stringify(selectedService.toString())};

        // Handle the message inside the webview
        window.addEventListener('message', event => {
            const message = event.data; // The JSON data our extension sent
            switch (message.command) {
                case 'update':
                    docUri = message.docUri;
                    updatedJSON = message.json
                    drawAPIEditor();
                    break;
            }
        });

        function getSwaggerJson(docUri, serviceName) {
            return new Promise((resolve, reject) => {
                webViewRPCHandler.invokeRemoteMethod('getSwaggerDef', [docUri, serviceName], (resp) => {
                    resolve(resp);
                });
            })
        }

        function onDidJsonChange(event, changedObj, oasJson) {
            webViewRPCHandler.invokeRemoteMethod('triggerSwaggerDefChange', [JSON.stringify(oasJson), docUri]);
        }

        function drawAPIEditor() {
            if(updatedJSON === '') {
                getSwaggerJson(docUri, selectedService).then((response)=>{
                    try {
                        let width = window.innerWidth - 6;
                        let height = window.innerHeight;
                        debugger;
                        ballerinaDiagram.renderBallerinaApiEditor(document.getElementById("api-visualizer"), JSON.stringify(response.ballerinaOASJson), onDidJsonChange);
                    } catch (e) {
                        console.log(e.stack);
                    }
                })
            } else {
                console.log(updatedJSON);
                ballerinaDiagram.renderBallerinaApiEditor(document.getElementById("api-visualizer"), updatedJSON, onDidJsonChange);
            }

            vscode.postMessage({
                command: 'astModified'
            })
            
        }

        window.onresize = drawAPIEditor;
        drawAPIEditor();
        drawAPIEditor();

    `;

    return getLibraryWebViewContent(context, body, script, styles);
}
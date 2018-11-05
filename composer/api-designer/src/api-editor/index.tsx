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

import * as React from 'react';
import * as SwaggerParser from 'swagger-parser';
import { Message } from 'semantic-ui-react';

import { OpenApiContextProvider, OpenApiContext } from './context/open-api-context';
import OpenApiResourceList from './components/resource/resources';
import { OpenApiResource } from './components/resource/add-resource';

import 'semantic-ui-css/semantic.min.css';
import './components/style/main.less';

export interface OasProps {
    openApiJson: any,
    onDidAddResource?: Function,
    onDidAddOperation?: Function,
    onDidAddParameter?: Function,
    onDidDeleteOperation?: Function,
    onDidChange?: Function
}

export interface OpenApiState {
    openApiJson: any,
    isError: OpenApiError,
    actionState: OpenApiActionState
}

export interface OpenApiActionState {
    state: Boolean,
    message: string
}

export interface OpenApiError { 
    status: Boolean,
    inline: Boolean,
    message: string
}

enum EVENTS {
    ADD_RESOURCE,
    ADD_OPERATION,
    ADD_PARAMETER,
    DELETE_OPERATION,
    DELETE_RESOURCE,
    DELETE_PARAMETER
}

/**
 * Component which will visualize a given OAS Json
 * Compatible with OAS 3.x versions
 */
class OpenApiVisualizer extends React.Component<OasProps, OpenApiState> {
    constructor(props: OasProps) {
        super(props);
        this.state = {
            openApiJson: {},
            isError: {
                status: false,
                inline: true,
                message: ''
            },
            actionState: {
                state: false,
                message: '',
            }
        }

        this.onDidAddResource = this.onDidAddResource.bind(this);
        this.onDidAddOperation = this.onDidAddOperation.bind(this);
        this.onDidAddParameter = this.onDidAddParameter.bind(this);
        this.onDidDeleteOperation = this.onDidDeleteOperation.bind(this);
    }
    componentDidMount() {
        const { openApiJson } = this.props;

        this.validateJsonProp(openApiJson);
        this.validateOpenApiJson(openApiJson);
    }
    componentWillReceiveProps(nextProps: OasProps) {
        const { openApiJson } = nextProps;

        this.validateJsonProp(openApiJson);
        this.validateOpenApiJson(openApiJson);
    }

    /**
     * Event which will get triggered when a resource is added.
     * 
     * @param addedResource Resource object which is added
     */
    onDidAddResource(addedResource: OpenApiResource) {
        const { onDidAddResource, onDidChange } = this.props;
        const resourceName = addedResource.name.replace(' ','');

        this.setState(prevState => ({
            ...prevState,
            oasJson: {
                ...prevState.openApiJson,
                paths: {
                    ...prevState.openApiJson.paths,
                    ['/' + resourceName]: {}
                }
            }
        }), () => {
            if (this.state.openApiJson.paths['/' + resourceName]) {
                if (onDidAddResource) {
                    onDidAddResource(resourceName, this.state.openApiJson);
                }

                if (onDidChange) {
                    onDidChange(EVENTS.ADD_RESOURCE, resourceName, this.state.openApiJson);
                }

                this.setState({
                    actionState: {
                        state: true,
                        message: 'Added resource to the swagger.'
                    }
                });
            }
        })
    }
    onDidAddOperation() {

    }
    onDidAddParameter() {

    }
    onDidDeleteOperation() {

    }

    /**
     * Util method to validate Open API JSON.
     * 
     * @param json Open API JSON that needs to be validated
     * @param onvalidattion Function to be run as a callback after validation
     */
    validateOpenApiJson(json: string, onvalidattion?: Function) {
        debugger;
        SwaggerParser.validate(JSON.parse(json)).then(validjson => {
            this.setState({
                openApiJson: validjson
            });

            if (onvalidattion) {
                onvalidattion(validjson);
            };
        }).catch(error => {
            this.setState({
                openApiJson: json,
                isError: {
                    status: true,
                    inline: true,
                    message: error.message
                }
            })
        })
    }

    /**
     * Util method to check the prop contains a json to be processed.
     * 
     * @param json JSON String which needs to be checked for undefined
     */
    validateJsonProp(json: string) {
        if (!json) {
            this.setState({
                isError: {
                    status: true,
                    inline: false,
                    message: 'Open API Specification complient JSON String is required.'
                }
            })
        }
    }
    
    render() {
        const { isError: { status, inline }, isError, openApiJson, openApiJson: { paths, info }, actionState } = this.state;
        const appContext: OpenApiContext = {
            openApiJson,
            onDidAddResource: this.onDidAddResource,
            onDidAddOperation: this.onDidAddOperation,
            onDidAddParameter: this.onDidAddParameter,
            onDidDeleteOperation: this.onDidDeleteOperation
        }
        if (status && !inline) {
            return (
                <Message error content={isError.message} />
            )
        }

        return (
            <OpenApiContextProvider value={appContext}>
                {isError.status && isError.inline &&
                    <Message error content={isError.message} />
                }
                {info && 
                    <React.Fragment>
                        <div className='oas-header'>
                            <h1>
                                {info.title}
                                <span>{openApiJson.host}{openApiJson.basePath}</span>
                            </h1>
                            <span className='version'>{info.version}</span>
                        </div> 
                        {info.description && 
                            <div className='oas-details'>
                                <p>{info.description}</p>
                            </div>
                        }
                    </React.Fragment>
                }
                {(() => {
                    if (actionState.state) {
                        return <Message success content={actionState.message} />
                    } else {
                        return <Message error content={actionState.message} />
                    }
                })()}
                <OpenApiResourceList openApiResources={paths} />
            </OpenApiContextProvider>
        );
    }
}

export default OpenApiVisualizer;
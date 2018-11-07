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
import { OpenApiOperation } from './components/operation/add-operation';
import { OpenApiResponse } from './components/parameter/add-response';
import HideComponent from './util-components/hider'; 

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
    state: string,
    message: string
}

export interface OpenApiError { 
    status: boolean,
    inline: boolean,
    message: string
}

enum EVENTS {
    ADD_RESOURCE,
    ADD_OPERATION,
    ADD_PARAMETER,
    ADD_RESPONSE,
    DELETE_OPERATION,
    DELETE_RESOURCE,
    DELETE_PARAMETER,
    DELETE_RESPONSE
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
                inline: false,
                message: ''
            },
            actionState: {
                state: '',
                message: '',
            }
        }

        this.onDidAddResource = this.onDidAddResource.bind(this);
        this.onDidAddOperation = this.onDidAddOperation.bind(this);
        this.onDidAddParameter = this.onDidAddParameter.bind(this);
        this.onDidAddResponse = this.onDidAddResponse.bind(this);
        this.onDidDeleteOperation = this.onDidDeleteOperation.bind(this);
    }
    componentDidMount() {
        const { openApiJson } = this.props;
        debugger;

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
            openApiJson: {
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
                        state: 'success',
                        message: 'Resource : ' + resourceName + ' added successfully to the definition.'
                    }
                });
            }
        })
    }

    /**
     * 
     * Event which will get triggered when a resource is added.
     * 
     * @param operationsObj operations object which is added    
     */
    onDidAddOperation(operationsObj: OpenApiOperation) {
        const { onDidAddOperation, onDidChange } = this.props;
        const path = operationsObj.path;

        this.setState(prevState => ({
            ...prevState,
            openApiJson: {
                ...prevState.openApiJson,
                paths: {
                    ...prevState.openApiJson.paths,
                    [path] : {
                        ...prevState.openApiJson.paths[path],
                        [operationsObj.method] : {
                            consumes : [],
                            description: operationsObj.description,
                            operationId: operationsObj.id,
                            parameters: [],
                            produces : ["application/xml", "application/json"],
                            responses :{},
                            security: [],
                            summary: operationsObj.name,
                            tags:[]
                        }
                    }
                }
            }
        }),()=>{

            if (onDidAddOperation) {
                onDidAddOperation(operationsObj, this.state.openApiJson);
            }

            if (onDidChange) {
                onDidChange(EVENTS.ADD_OPERATION, operationsObj, this.state.openApiJson);
            }

            this.setState({
                actionState: {
                    state: 'success',
                    message: 'Added operation to ' + path
                }
            })

        });
    }
    onDidAddParameter() {

    }

    /**
     * Event which will get triggerred when a new response is added
     * 
     * @param responseObj response object which is added
     */
    onDidAddResponse(responseObj: OpenApiResponse ) {
        const { onDidAddResource, onDidChange } = this.props;
        const path = responseObj.resourcePath;
        const method = responseObj.operation;

        this.setState(prevState => ({
            ...prevState,
            openApiJson: {
                ...prevState.openApiJson,
                paths: {
                    ...prevState.openApiJson.paths,
                    [path] : {
                        ...prevState.openApiJson.paths[path],
                        [method] : {
                            ...prevState.openApiJson.paths[path][method],
                            responses : {
                                ...prevState.openApiJson.paths[path][method].responses,
                                [responseObj.status] : {
                                    description: responseObj.description
                                }
                            }
                        }
                    }
                }
            }
        }),()=>{

            if (onDidAddResource) {
                onDidAddResource(responseObj, this.state.openApiJson);
            }

            if (onDidChange) {
                onDidChange(EVENTS.ADD_RESPONSE, responseObj, this.state.openApiJson);
            }

            debugger;
            this.setState({
                actionState: {
                    state: 'success',
                    message: 'Successfully added response to ' + path + method
                }
            })

        });
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
        SwaggerParser.validate(JSON.parse(json)).then(validjson => {
            this.setState({
                openApiJson: validjson
            });

            if (onvalidattion) {
                onvalidattion(validjson);
            };
        }).catch(error => {
            debugger;
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
            debugger;
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
            onDidAddResponse: this.onDidAddResponse,
            onDidDeleteOperation: this.onDidDeleteOperation
        }

        if (status && !inline) {
            return (
                <HideComponent hideOn={1000}>
                    <Message error content={isError.message} />
                </HideComponent>
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
                                <p></p>
                            </div>
                        }
                    </React.Fragment>
                }
                {(() => {
                    if (actionState.state === 'success') {
                        return <Message success content={actionState.message} />
                    } else if (actionState.state === 'error') {
                        return <Message error content={actionState.message} />
                    } else {
                        return '';
                    }
                })()}
                <OpenApiResourceList openApiResources={paths} />
            </OpenApiContextProvider>
        );
    }
}

export default OpenApiVisualizer;
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
import { Form, Button } from 'semantic-ui-react';

export interface OpenApiAddOperationProps {
    openApiJson: any,
    onAddOperation: Function,
    resourcePath: string
}

export interface OpenApiAddOperationState {
    operationObject: OpenApiOperation,
    operationMethods: Array<OpenApiOperationMethod>,
}

export interface OpenApiOperation {
    id: string,
    name: string,
    description: string,
    method: string[],
    path: string,
}

export interface OpenApiOperationMethod {
    key: string,
    text: string,
    value: string
}

class OpenApiAddOperation extends React.Component<OpenApiAddOperationProps, OpenApiAddOperationState> {
    constructor(props: OpenApiAddOperationProps) {
        super(props);

        this.state = {
            operationObject: {
                id: '',
                name: '',
                description: '',
                method: [],
                path: this.props.resourcePath,
            },
            operationMethods: []
        }

        
    }

    componentDidMount() {
        this.populateOperationMethods();
    }

    populateOperationMethods() {
        const { resourcePath, openApiJson } = this.props;
        let methodOpts: Array<OpenApiOperationMethod> = [];

        let availableMethods = ['GET','POST','PUT','DELETE','PATCH','HEAD','OPTIONS'].filter(method => {
            return !Object.keys(openApiJson.paths[resourcePath]).includes(method.toLowerCase())
        })

        availableMethods.forEach((method)=>{
            methodOpts.push({
                key: method.toLowerCase(),
                text: method,
                value: method.toLowerCase(),
            })
        });

        this.setState({
            operationMethods: methodOpts,
        })
    }

    render() {
        const { onAddOperation } = this.props;
        const { operationMethods } = this.state;

        if(operationMethods.length == 0) {
            return (
                <Form size='mini' className='add-operation'>
                    <p>The selected resource contains all the method implementations.</p>
                </Form>
            )
        }

        return (
            <Form size='mini' className='add-operation'>
                <Form.Field>
                    <label>Name</label>
                    <input placeholder='Short Summery' onChange={(e) => this.setState({ 
                        operationObject: {
                            ...this.state.operationObject,
                            name: e.target.value,
                            id: e.target.value.replace(' ','')
                        }
                    })} />
                </Form.Field>
                <Form.Group inline>
                    <label>Methods</label>
                    {operationMethods.map((method)=>{
                        return (
                            <Form.Checkbox
                                size='mini'
                                label={method.text}
                                value={method.value}
                                onChange={(e: React.SyntheticEvent, data: any) => {
                                    if(data.checked) {
                                        this.setState({
                                            operationObject: {
                                                ...this.state.operationObject,
                                                method:  [...this.state.operationObject.method, data.label],
                                            }
                                        })
                                    }
                                }}
                            />
                        )
                    })}
                </Form.Group>
                <Form.Field>
                    <Form.TextArea label='Description' placeholder='Tell us more about...' onChange={(e) => this.setState({ 
                        operationObject: {
                            ...this.state.operationObject,
                            description: e.currentTarget.value
                        }
                    })} />
                </Form.Field>
                <Button size='mini' onClick={() => {
                    onAddOperation(this.state.operationObject)
                }}>Save</Button>
            </Form>
        )
    }
}

export default OpenApiAddOperation;
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
    method: string,
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
        return (
            <div></div>
        )
    }
}

export default OpenApiAddOperation;
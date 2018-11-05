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

export interface OpenApiAddResourceProps {
    onDidAddResource: Function
}

export interface OpenApiAddResourceState {
    openApiResourceObj: OpenApiResource
}

export interface OpenApiResource {
    name: string
}

class OpenApiAddResource extends React.Component<OpenApiAddResourceProps, OpenApiAddResourceState> {
    constructor(props: OpenApiAddResourceProps) {
        super(props);
    }

    render() {
        const { onDidAddResource } = this.props;

        return (
            <Form size='mini' className='add-resource'>
                <Form.Field>
                    <label>Resource Name</label>
                    <input placeholder='Example: /users/{userId}' onChange={(e) => this.setState({ 
                        openApiResourceObj: {
                            name: e.target.value
                        }
                    })} />
                </Form.Field>
                <Button size='tiny' onClick={() => {
                    onDidAddResource(this.state.openApiResourceObj);
                }}>Save Resource</Button>
            </Form>
        )
    }
}

export default OpenApiAddResource;
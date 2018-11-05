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
import { Form, Button, Select } from 'semantic-ui-react';

class OpenApiAddResponse extends React.Component {
    render() {
        return (
            <Form size='mini' className='add-operation'>
                <Form.Field>
                    <Form.Field control={Select} label='Response Status' placeholder='200 - OK' />
                </Form.Field>
                <Form.Field>
                    <Form.TextArea label='Description' placeholder='Tell us more about...' />
                </Form.Field>
                <Button size='mini'>Save</Button>
            </Form>
        )
    }
}

export default OpenApiAddResponse;
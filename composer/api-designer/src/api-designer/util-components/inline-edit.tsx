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

export interface InlineEditProps {
    text: string
    onChange?: Function
    customClass?: string
    activeStateClass?: string
    isEditable: boolean
}

export interface InlineEditState {
    text: string
    isEditing: boolean
}

class InlineEdit extends React.Component<InlineEditProps, InlineEditState> {
    constructor(props: InlineEditProps) {
        super(props);

        this.state = {
            text: '',
            isEditing: false
        }

        this.enableEditing = this.enableEditing.bind(this);
    }

    enableEditing() {
        this.setState({
            isEditing: true,
            text: this.props.text
        })
    }

    render() {
        const { text, isEditable } = this.props;
        const { isEditing } = this.state; 

        if (!isEditable) {
            return <span className='inline-editor disabled'>{text}</span>
        } else if (!isEditing) {
            return <span className='inline-editor non-editing' onClick={this.enableEditing}>{text}</span>
        } else {
            return <input className='inline-editor editing' value={text} />
        }
    }
}

export default InlineEdit;
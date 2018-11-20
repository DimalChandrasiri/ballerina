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
    placeholderText?: string
    isTextArea?: boolean
}

export interface InlineEditState {
    stateText: string
    isEditing: boolean
    elementType: string
}

class InlineEdit extends React.Component<InlineEditProps, InlineEditState> {
    constructor(props: InlineEditProps) {
        super(props);

        this.state = {
            stateText: this.props.text,
            isEditing: false,
            elementType: 'input'
        }

        this.enableEditing = this.enableEditing.bind(this);
        this.handleFocusOut = this.handleFocusOut.bind(this);
        this.handleTextChange = this.handleTextChange.bind(this);
    }

    enableEditing(e : React.MouseEvent<HTMLSpanElement>) {
        e.stopPropagation();
        this.setState({
            isEditing: true,
        });
    }

    handleFocusOut() {
        this.setState({
            isEditing: false,
            stateText: this.state.stateText
        });
    }

    handleTextChange(e : React.ChangeEvent<HTMLInputElement>) {
        this.setState({
            stateText: e.target.value
        },()=>{
            if (this.props.onChange) {
                this.props.onChange()
            }
        });
    }

    render() {
        const { isEditable, placeholderText, customClass, isTextArea } = this.props;
        const { isEditing, stateText } = this.state;

        debugger;
        if(!isEditable) {
            return <span className='inline-editor disabled'>{stateText}</span>
        } else if (!isEditing && stateText !== undefined && stateText !== '') {
            return (
                <div className={'inline-editor non-editing ' + customClass} onClick={this.enableEditing}>
                    <span>
                        {stateText}
                    </span>
                </div>
            ) 
        } else if (!isEditing && (stateText === undefined || stateText == '')) {
            return (
                <div className={'inline-editor no-text ' + customClass} onClick={this.enableEditing}>
                    <span>
                        {placeholderText}
                    </span>
                </div>
            ) 
        } else {
            if (isTextArea) {
                return (
                    <div className={'inline-editor editing ' + customClass}>
                        <textarea 
                            autoFocus
                            onBlur={this.handleFocusOut}
                            placeholder={placeholderText}
                        >
                            {stateText}
                        </textarea>
                    </div>
                )
            } else { 
                return (
                    <div className={'inline-editor editing ' + customClass}>
                        <input
                            autoFocus
                            value={stateText} 
                            onBlur={this.handleFocusOut}
                            onChange={this.handleTextChange}
                            placeholder={placeholderText}
                            onClick={(e)=>{
                                e.stopPropagation();
                            }}
                        />
                    </div>
                )
            }
        }
    }
}

export default InlineEdit;
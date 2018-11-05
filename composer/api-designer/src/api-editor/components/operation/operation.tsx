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
import { Accordion, Icon, AccordionTitleProps } from 'semantic-ui-react';

import OpenApiAddParameter from '../parameter/add-parameter';
import OpenApiAddResponse from '../parameter/add-response';

export interface OpenApiResourceProps {
    resourcePath: string,
    operationType: string,
    operationObject: any,
    activeIndex: number,
    currIndex: number,
    handleExpand: (event: React.MouseEvent<HTMLDivElement>, data: AccordionTitleProps) => void,
    onDeleteOperation: Function
}

export interface OpenApiResourceState {
    showAddResponse: boolean,
    showAddParameter: boolean
}

class OpenApiResource extends React.Component<OpenApiResourceProps, OpenApiResourceState> {
    constructor(props: OpenApiResourceProps) {
        super(props);

        this.state = {
            showAddParameter: false,
            showAddResponse: false
        }
    }

    handleShowAddParameter () {
        const { showAddParameter } = this.state;
        this.setState({
            showAddParameter: !showAddParameter,
        });
    }

    handleShowAddResponse () {
        const { showAddResponse } = this.state;
        this.setState({
            showAddResponse: !showAddResponse,
        });
    }

    render() {
        const {
            resourcePath, operationType, operationObject, activeIndex, currIndex, handleExpand, onDeleteOperation
        } = this.props;
        const { showAddResponse, showAddParameter } = this.state;

        return (
            <div className={'operation '  + operationType}>
                <Accordion.Title className='op-title ' index={currIndex} onClick={handleExpand}>
                    <span className='op-method'>{operationType}</span>
                    <span className='op-summary'>{operationObject.summary}</span>
                    <Icon
                        className='delete-op'
                        onClick={(event: React.MouseEvent<HTMLButtonElement>) => {
                            event.stopPropagation();
                            onDeleteOperation({
                                operation: operationType,
                                path: resourcePath,
                                operationObj: operationObject
                            })
                        }}
                        name='trash alternate'
                    />
                </Accordion.Title>
                <Accordion.Content active={activeIndex === currIndex}>
                    <p>{operationObject.description}</p>
                    
                    <div className='op-section'>
                        <div className='title'>
                            <p>Parameters</p>
                            <a onClick={this.handleShowAddParameter} >Add Parameter</a>
                        </div>
                        {showAddParameter &&
                            <OpenApiAddParameter/>
                        }
                    </div>
                    
                    <div className='op-section '>
                        <div className='title'>
                            <p>Responses</p>
                            <a onClick={this.handleShowAddResponse} >Add Response</a>
                        </div>
                        {showAddResponse &&
                            <OpenApiAddResponse />
                        }
                    </div>
                   
                </Accordion.Content>
            </div>
        )
    }
}

export default OpenApiResource;
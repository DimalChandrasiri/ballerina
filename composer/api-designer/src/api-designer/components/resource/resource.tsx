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
import { Accordion, Icon, Button, AccordionTitleProps } from 'semantic-ui-react';

import OpenApiOperationsList from '../operation/operations';
import InlineEdit from '../../util-components/inline-edit';

export interface OpenApiResourceProps {
    openApiResource: string,
    openApiOperations: any,
    activeIndex: number,
    currentIndex: number,
    onExpandEvent: (event: React.MouseEvent<HTMLDivElement>, data: AccordionTitleProps) => void,
    isExpandAll: boolean
}

export interface OpenApiResourceState {
    showAddOperation: boolean
}

class OpenApiResource extends React.Component<OpenApiResourceProps, OpenApiResourceState> {
    constructor(props: OpenApiResourceProps) {
        super(props);

        this.state = {
            showAddOperation: false
        }

        this.handleShowAddOperation = this.handleShowAddOperation.bind(this);
    }

    handleShowAddOperation(event: React.MouseEvent<HTMLButtonElement>) {
        event.stopPropagation();
        const { showAddOperation } = this.state;
        this.setState({
            showAddOperation: !showAddOperation,
        });
    }

    render() {
        const {
            openApiResource, openApiOperations, activeIndex, currentIndex, onExpandEvent, isExpandAll
        } = this.props;

        const { showAddOperation } = this.state; 

        return (
            <div className='resource'>
                <Accordion.Title className='res-title' index={currentIndex} onClick={onExpandEvent}>
                    <Icon name={isExpandAll || activeIndex === currentIndex ? 'chevron down' : 'chevron right'}/>
                    <InlineEdit model={openApiOperations} attribute={openApiResource} isEditable text={openApiResource} placeholderText='Add a description' />
                    {isExpandAll || activeIndex === currentIndex ? 
                        <Button title='Add operation to resource.' size='mini' compact className='add-operation-action' circular icon='plus' onClick={(e)=>{this.handleShowAddOperation(e)}} /> : ''
                    }
                </Accordion.Title>
                <Accordion.Content className='resource-content' active={isExpandAll || activeIndex === currentIndex}>
                    <OpenApiOperationsList openApiOperations={openApiOperations} resourcePath={openApiResource} showAddOperation={showAddOperation} />
                </Accordion.Content>
            </div>
        )
    }
}

export default OpenApiResource;
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
import { Accordion, Button, Icon, AccordionTitleProps } from 'semantic-ui-react';

import { OpenApiContextConsumer, OpenApiContext } from '../../context/open-api-context';
import OpenApiAddResource from './add-resource';
import OpenApiResource from './resource';

export interface OasResourceListProps { 
    openApiResources: any
}

export interface OpenApiResourceListState {
    expandAll: boolean,
    showAddResource: boolean,
    activeIndex: number
}

class OpenApiResourceList extends React.Component<OasResourceListProps, OpenApiResourceListState> {
    constructor(props: OasResourceListProps) {
        super(props);

        this.showOpenApiAddResource = this.showOpenApiAddResource.bind(this)
        this.expandResource = this.expandResource.bind(this);
        this.expandAllResources = this.expandAllResources.bind(this);
    }

    showOpenApiAddResource() {
        const { showAddResource } = this.state;
        this.setState({
            showAddResource: !showAddResource
        })
    }

    expandAllResources() {
        const { expandAll } = this.state;
        this.setState({
            expandAll: !expandAll
        })
    }

    expandResource(event: React.MouseEvent<HTMLDivElement>, data: AccordionTitleProps): void {
        const { index } = data;
        const { activeIndex } = this.state;
        let newIndex: number;

        if(this.state.expandAll) {
            newIndex = -1;
        } else {
            newIndex = activeIndex === Number(index) ? -1 : Number(index);
        }

        this.setState({ activeIndex: newIndex });
    }

    render() {
        const { openApiResources } = this.props;
        const { expandAll, showAddResource, activeIndex } = this.state;
        return (
            <div className='open-api-resource-list-container'>
                <Button size='mini' icon labelPosition='left' onClick={this.showOpenApiAddResource}>
                    <Icon name='plus' />
                    Add Resource
                </Button>
                <Button size='mini' icon labelPosition='left' floated='right' onClick={this.expandAllResources}>
                    <Icon name={expandAll ? 'compress' : 'expand'} />
                    {expandAll ? 'Collapse All' : 'Expand All'}
                </Button>
                {showAddResource &&
                    <OpenApiContextConsumer>
                        {(context: OpenApiContext) => {
                            return (
                                <OpenApiAddResource onDidAddResource={context.onDidAddResource} />
                            )
                        }}
                    </OpenApiContextConsumer>
                }
                {openApiResources && Object.keys(openApiResources).length > 0 &&
                    <Accordion className='open-api-resource-list'>
                        {Object.keys(openApiResources).map((openApiResource, index) => {
                            return (
                                <OpenApiResource 
                                    openApiResource = { openApiResource }
                                    openApiOperations = { openApiResources[openApiResource] }
                                    activeIndex = { activeIndex }
                                    currentIndex = { index }
                                    onExpandEvent = { this.expandResource }
                                    isExpandAll = { expandAll }
                                />
                            )
                        })}
                    </Accordion>
                }
            </div>
        );
    }
}

export default OpenApiResourceList;
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

import React from 'react';
import PropTypes from 'prop-types';
import { Accordion, Icon, Button } from 'semantic-ui-react';

import OasOperations from '../operations/operations';

/**
 * Resource Component
 */
/* eslint-disable */
class OasResource extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            showAddOperation: false,
        };

        this.handleShowAddOperation = this.handleShowAddOperation.bind(this);
    }

    /**
     * Event handler for show add resource form
     */
    handleShowAddOperation(e) {
        e.stopPropagation();
        const { showAddOperation } = this.state;
        this.setState({
            showAddOperation: !showAddOperation,
        });
    }

    /**
     * @returns {React.ReactElement} React Element
     */
    render() {
        const {
            oasOps, handleExpand, activeIndex, resPath, currIndex, expandAll
        } = this.props;
        return (
            <div className='resource'>
                <Accordion.Title className='res-title' index={currIndex} onClick={handleExpand}>
                    <Icon name={expandAll || activeIndex === currIndex ? 'chevron down' : 'chevron right'}/>
                    {resPath}
                    {expandAll || activeIndex === currIndex ? 
                        <Button Title='Add operation to resource.' icon size='mini' circular className='add-operation-action' onClick={(e)=>{this.handleShowAddOperation(e)}}>
                            <Icon name='plus' />
                        </Button> : ''
                    }
                </Accordion.Title>
                <Accordion.Content className='resource-content' active={expandAll || activeIndex === currIndex}>
                    <OasOperations oasOperations={oasOps} path={resPath} showAddOperation={this.state.showAddOperation} />
                </Accordion.Content>
            </div>
        );
    }
}

OasResource.propTypes = {
    oasOps: PropTypes.object.isRequired,
    activeIndex: PropTypes.number.isRequired,
    handleExpand: PropTypes.func.isRequired,
    currIndex: PropTypes.number.isRequired,
    resPath: PropTypes.string.isRequired,
    expandAll: PropTypes.bool.isRequired,
};
export default OasResource;

/**
 * Copyright 2019 Enterprise Proxy Authors
 *
 * Licensed under the Apache License, Version 2.0 (the “License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an “AS IS” BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.klaytn.enterpriseproxy.rpc.common.model;

import net.sf.json.JSONSerializer;
import java.io.Serializable;


public class FilterOptions implements Serializable {
    private static final long serialVersionUID=6703441424004627278L;

    /**
     * from block
     * @type string
     */
    private String fromBlock;


    /**
     * to block
     * @type string
     */
    private String toBlock;


    /**
     * address
     * @type address
     */
    private String[] address;


    /**
     * topics
     * @type string[]
     */
    private String[] topics;


    /**
     * block hash
     * @type string
     */
    private String blockHash;




    public String getFromBlock() {
        return fromBlock;
    }

    public void setFromBlock(String fromBlock) {
        this.fromBlock=fromBlock;
    }

    public String getToBlock() {
        return toBlock;
    }

    public void setToBlock(String toBlock) {
        this.toBlock=toBlock;
    }

    public String[] getAddress() {
        return address;
    }

    public void setAddress(String[] address) {
        this.address=address;
    }

    public String[] getTopics() {
        return topics;
    }

    public void setTopics(String[] topics) {
        this.topics=topics;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash=blockHash;
    }

    @Override
    public String toString() {
        return JSONSerializer.toJSON(this).toString();
    }
}
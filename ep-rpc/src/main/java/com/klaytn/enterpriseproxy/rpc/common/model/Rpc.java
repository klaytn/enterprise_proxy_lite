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


public class Rpc implements Serializable {
    private static final long serialVersionUID = -1916488242841138076L;

    /**
     * jsonrpc version
     * @type string
     */
    private String jsonrpc = "2.0";


    /**
     * jsonrpc id
     * @type number
     */
    private int id = 1;
    



    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return JSONSerializer.toJSON(this).toString();
    }
}
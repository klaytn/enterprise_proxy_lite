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

import com.klaytn.enterpriseproxy.utils.ObjectUtil;
import net.sf.json.JSONSerializer;


public class RpcResponse extends Rpc {
    /**
     * response result
     * @type object
     */
    private Object result;


    /**
     * response error
     * @type string
     */
    private Object error;




    public Object getResult() {
        return (ObjectUtil.isEmpty(result)) ? "" : result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getError() {
        return (ObjectUtil.isEmpty(error)) ? "" : error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return JSONSerializer.toJSON(this).toString();
    }
}
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
package com.klaytn.enterpriseproxy.api.common.model;

import net.sf.json.JSONSerializer;
import com.klaytn.enterpriseproxy.utils.ObjectUtil;
import java.io.Serializable;


public class ApiResponse implements Serializable {
    private static final long serialVersionUID = 7310228091432611686L;

    /**
     * API Response StatCode
     * @type number
     */
    private int code;


    /**
     * response Target
     *  - rpc,transaction,api...
     * @type string
     */
    private String target;


    /**
     * API Response StatResult
     * @type object
     */
    private Object result;


    /**
     * API Response data
     * @type object
     */
    private Object data;




    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Object getResult() {
        return (ObjectUtil.isEmpty(result)) ? "" : result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getData() {
        return (ObjectUtil.isEmpty(data)) ? "" : data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSONSerializer.toJSON(this).toString();
    }
}
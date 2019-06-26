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


public class KlayCall implements Serializable {
    private static final long serialVersionUID=6160226229875223175L;

    /**
     * from address
     * @type string
     */
    private String from;


    /**
     * to address
     * @type string
     */
    private String to;


    /**
     * gas
     * @type number
     */
    private String gas;


    /**
     * gas price
     * @type number
     */
    private String gasPrice;


    /**
     * value
     * @type number
     */
    private String value;


    /**
     * data
     * @type string
     */
    private String data;


    /**
     * nonce
     * @type number
     */
    private String nonce;


    

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from=from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to=to;
    }

    public String getGas() {
        return gas;
    }

    public void setGas(String gas) {
        this.gas=gas;
    }

    public String getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(String gasPrice) {
        this.gasPrice=gasPrice;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value=value;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data=data;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce=nonce;
    }

    @Override
    public String toString() {
        return JSONSerializer.toJSON(this).toString();
    }
}

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


public enum ApiResponseCode {
    /**
     * 999  : UNKNOWN ERROR
     * 998  : PARAMETER INVALID
     * 997  : EMPTY DATA
     * 996  : RPC CALL ERROR
     * 995  : DB EXECUTE ERROR
     */
    UNKNOWN(999,"UNKNOWN"),
    PARAMETER_INVALID(998,"PARAMETER INVALID"),
    EMPTY_DATA(997,"EMPTY DATA"),
    RPC_CALL_ERROR(996,"RPC CALL ERROR"),
    DB_ERROR(995,"DB EXECUTE ERROR"),


    /**
     * 899  : TRANSACTION ERROR
     */
    TX_ERROR(899,"TRANSACTION ERROR"),


    /**
     * 799  : DISABLE APIS
     */
    DISABLE_APIS(799,"DISABLE API INTERFACE"),

    
    /**
     * 0    : SUCCESS
     */
    SUCCESS(0,"SUCCESS");



    /**
     * RESPONSE CODE
     *
     * @type number
     */
    private int statusCode;


    /**
     * RESPONSE MESSAGE
     *
     * @type string
     */
    private String message = "";




    ApiResponseCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }


    public int getStatusCode() {
        return statusCode;
    }


    public String getMessage() {
        return message;
    }
}
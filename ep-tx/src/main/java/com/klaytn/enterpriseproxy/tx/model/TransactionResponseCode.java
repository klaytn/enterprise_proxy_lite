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
package com.klaytn.enterpriseproxy.tx.model;


public enum TransactionResponseCode {
    /**
     * 999  : UNKNOWN
     * 998  : EMPTY DATA
     * 997  : TRANSACTION REQUIREMENT PARAMETER INVALID
     * 996  : TRANSACTION HASH FORMAT INVALID
     * 995  : TRANSACTION TYPE INVALID
     */
    UNKNOWN(999,"UNKNOWN"),
    EMPTY_DATA(998,"EMPTY DATA"),
    TX_PARAMETER_INVALID(997,"TRANSACTION REQUIREMENT PARAMETER INVALID"),
    TX_FORMAT_INVALID(996,"TRANSACTION HASH FORMAT INVALID"),
    TX_TYPE_INVALID(995,"TRANSACTION TYPE INVALID"),


    /**
     * 899  : SIGNED TRANSACTION ERROR
     * 898  : TRANSACTION ERROR
     * 897  : FEE DELEGATED TRANSACTION ERROR
     * 896  : FEE DELEGATED TRANSACTION PARAMETER ERROR
     * 895  : FEE PAYER NEED TO CHECK CONFIGURATION INFORMATION
     */
    SIGNED_TX_ERROR(899,"SIGNED TRANSACTION ERROR"),
    TX_ERROR(898,"TRANSACTION ERROR"),
    FEE_DELEGATED_TX_ERROR(897,"FEE DELEGATED TRANSACTION ERROR"),
    FEE_DELEGATED_TX_PARAMETER_ERROR(896,"FEE DELEGATED TRANSACTION PARAMETER ERROR"),
    FEE_PAYER_NEED_TO_CHECK_CONFIGURATION_INFORMATION(895,"FEE PAYER NEED TO CHECK CONFIGURATION INFORMATION"),


    /**
     * 0    : SUCCESS
     */
    SUCCESS(0,"SUCCESS");




    /**
     * result code
     * @type int
     */
    private int code;


    /**
     * result message
     * @type string
     */
    private String message;




    private TransactionResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }
}
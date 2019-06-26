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
package com.klaytn.enterpriseproxy.tx.exception;

import com.klaytn.enterpriseproxy.tx.model.TransactionResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TransactionException extends Exception {
    private static final Logger logger = LoggerFactory.getLogger(TransactionException.class);

    /**
     * Exception error code
     * @type number
     */
    private int errorCode = 999;


    

    /**
     * ep-tx custom exception
     *
     * @param message
     * @param errorCode
     */
    public TransactionException(String message,int errorCode) {
        super(message);
        this.errorCode = errorCode;
        this._logger(message);
    }


    /**
     * ep-tx custom exception
     *
     * @param message
     */
    public TransactionException(String message) {
        super(message);
        this._logger(message);
    }


    /**
     * ep-tx custom exception
     *
     * @param cause
     */
    public TransactionException(Throwable cause) {
        super(cause);
        this._logger(cause);
    }


    /**
     * ep-tx custom exception
     * - logger
     *
     * @param resultCode
     * @param cause
     */
    public TransactionException(TransactionResponseCode resultCode,Throwable cause) {
        super(cause.getLocalizedMessage());
        this.errorCode = resultCode.getCode();
        this._logger(cause);
    }


    /**
     * get error code
     *
     * @return
     */
    public int getErrorCode() {
        return this.errorCode;
    }


    /**
     * logger write
     *
     * @param object
     */
    private void _logger(Object object) {
        logger.error("{{{TRANSACTION EXCEPTION ERROR CODE " + getErrorCode() + " }}}",object);
    }
}
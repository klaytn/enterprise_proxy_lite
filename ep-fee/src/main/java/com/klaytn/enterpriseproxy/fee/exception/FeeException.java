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
package com.klaytn.enterpriseproxy.fee.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FeeException extends Exception {
    private static final Logger logger = LoggerFactory.getLogger(FeeException.class);




    /**
     * fee exception
     *
     * @param message
     */
    public FeeException(String message) {
        super(message);
        this._error(message);
    }


    /**
     * fee exception
     *
     * @param throwable
     */
    public FeeException(Throwable throwable) {
        super(throwable);
        this._error(throwable.getLocalizedMessage());
    }


    /**
     * error logger
     *
     * @param message
     */
    private void _error(String message) {
        logger.error("{{{ FEE EXCEPTION ERROR CAUSE : " + message + " }}}");
    }
}
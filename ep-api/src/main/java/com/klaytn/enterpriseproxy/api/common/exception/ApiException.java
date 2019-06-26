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
package com.klaytn.enterpriseproxy.api.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.joda.time.LocalDateTime;
import org.springframework.http.HttpStatus;


public class ApiException extends Throwable {
    /**
     * HTTP STATUS
     * @private
     */
    private HttpStatus status;


    /**
     * TIMESTAMP
     * @private
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;


    /**
     * ERROR MESSAGE
     * @private
     */
    private String message;


    /**
     * ERROR CODE
     * @private
     */
    private int code;




    private ApiException() {
        this.timestamp = LocalDateTime.now();
    }


    ApiException(HttpStatus status) {
        this();
        this.status = status;
    }


    ApiException(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = ex.getLocalizedMessage();
    }


    ApiException(HttpStatus status, int code, Throwable ex) {
        this();
        this.status = status;
        this.code = code;
        this.message = ex.getLocalizedMessage();
    }


    ApiException(HttpStatus status, int code, String message) {
        this();
        this.status = status;
        this.code = code;
        this.message = message;
    }


    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
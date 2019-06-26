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
package com.klaytn.enterpriseproxy.rpc.common.transfer;

import com.klaytn.enterpriseproxy.rpc.common.handler.RpcHandler;
import com.klaytn.enterpriseproxy.utils.ObjectUtil;
import com.klaytn.enterpriseproxy.utils.StringUtil;
import feign.Feign;
import feign.Retryer;
import feign.error.AnnotationErrorDecoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import okhttp3.ConnectionPool;
import com.klaytn.enterpriseproxy.rpc.common.model.RpcRequest;
import com.klaytn.enterpriseproxy.rpc.common.model.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;


public class RpcTransfer {
    private static final Logger logger = LoggerFactory.getLogger(RpcTransfer.class);


    /**
     * target host
     * @type string
     */
    private String targetHost;


    

    public RpcTransfer(String targetHost) {
        this.targetHost = targetHost;
    }


    /**
     * JSON RPC SERVER CALL
     *
     * @param request
     * @return
     */
    public RpcResponse call(RpcRequest request) {
        return call(request,this.targetHost);
    }


    /**
     * JSON RPC SERVER CALL
     *
     * @param request
     * @param host
     * @return
     */
    public RpcResponse call(RpcRequest request,String host) {
        if (ObjectUtil.isEmpty(request)) {
            return new RpcResponse();
        }

        if (ObjectUtil.isEmpty(request.getMethod())) {
            return new RpcResponse();
        }

        if (StringUtil.isEmpty(host)) {
            return new RpcResponse();
        }

        return this._call(request,StringUtil.trim(host));
    }


    /**
     * call rpc by OpenFeign
     *  - retryer
     *      = period : 100ms
     *      = maxPeriod : 300ms
     *      = maxAttempts : 2
     *
     *  - timeout
     *      = connectTimeoutMillis : 1500ms
     *      = readTimeoutMillis : 1500ms
     *
     * @param request
     * @param host
     * @return
     */
    private RpcResponse _call(RpcRequest request,String host) {
        okhttp3.OkHttpClient okHttpClient = new okhttp3.OkHttpClient()
                .newBuilder()
                .connectTimeout(1500,TimeUnit.MILLISECONDS)
                .readTimeout(1500,TimeUnit.MILLISECONDS)
                .connectionPool(new ConnectionPool(5,500,TimeUnit.MILLISECONDS))
                .build();

        RpcHandler handler = Feign.builder()
                .client(new OkHttpClient(okHttpClient))
                .retryer(new Retryer.Default(100,300,2))
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .errorDecoder(AnnotationErrorDecoder.builderFor(RpcHandler.class).build())
                .target(RpcHandler.class,host);

        RpcResponse response;

        try {
            response = handler.call(request);
            logger.info("@@@@ RPC RESPONSE [" + host + "] : " + response.toString() + " @@@@");
        }
        
        catch (Exception e) {
            logger.error("@@@@ RPC CALL ERROR [" + host + "] : " + e.getLocalizedMessage() + " @@@@");
            
            response = new RpcResponse();
            response.setResult("RPC ERROR");
            response.setError(e.getLocalizedMessage());
        }

        return response;
    }


    /**
     * get target host
     *
     * @return
     */
    public String getTargetHost() {
        return this.targetHost;
    }
}
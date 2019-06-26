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
package com.klaytn.enterpriseproxy.api.management.service;

import com.klaytn.enterpriseproxy.api.common.model.ApiResponse;
import com.klaytn.enterpriseproxy.api.common.util.ApiUtils;
import com.klaytn.enterpriseproxy.rpc.common.config.RpcProperties;
import com.klaytn.enterpriseproxy.rpc.management.personal.Personal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;


@Service
public class PersonalService {
    @Autowired
    private RpcProperties rpc;



    
    /**
     * import raw key
     *
     * @param httpServletRequest
     * @param keyData
     * @param passPhrase
     * @return
     */
    public ApiResponse importRawKey(HttpServletRequest httpServletRequest,String keyData,String passPhrase) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Personal.build(targetHost).importRawKey(keyData,passPhrase));
    }


    /**
     * list accounts
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse listAccounts(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Personal.build(targetHost).listAccounts());
    }


    /**
     * lock account
     *
     * @param httpServletRequest
     * @param address
     * @return
     */
    public ApiResponse lockAccount(HttpServletRequest httpServletRequest,String address) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Personal.build(targetHost).lockAccount(address));
    }


    /**
     * unlock account
     *
     * @param httpServletRequest
     * @param address
     * @param passPhrase
     * @param duration
     * @return
     */
    public ApiResponse unlockAccount(HttpServletRequest httpServletRequest,String address,String passPhrase,int duration) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Personal.build(targetHost).unlockAccount(address,passPhrase,duration));
    }


    /**
     * send transaction
     *
     * @param httpServletRequest
     * @param fromAddress
     * @param toAddress
     * @param gas
     * @param gasPrice
     * @param value
     * @param data
     * @param nonce
     * @param passPhrase
     * @return
     */
    public ApiResponse sendTransaction(HttpServletRequest httpServletRequest,String fromAddress,String toAddress,String gas,String gasPrice,String value,String data,String nonce,String passPhrase) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Personal.build(targetHost).sendTransaction(fromAddress,toAddress,gas,gasPrice,value,data,nonce,passPhrase));
    }


    /**
     * sign
     *
     * @param httpServletRequest
     * @param message
     * @param account
     * @param password
     * @return
     */
    public ApiResponse sign(HttpServletRequest httpServletRequest,String message,String account,String password) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Personal.build(targetHost).sign(message,account,password));
    }


    /**
     * ecrecover
     *
     * @param httpServletRequest
     * @param message
     * @param signature
     * @return
     */
    public ApiResponse ecRecover(HttpServletRequest httpServletRequest,String message,String signature) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Personal.build(targetHost).ecRecover(message,signature));
    }
}

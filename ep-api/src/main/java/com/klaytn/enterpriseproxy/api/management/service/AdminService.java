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
import com.klaytn.enterpriseproxy.rpc.management.admin.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;


@Service
public class AdminService {
    @Autowired
    private RpcProperties rpc;




    /**
     * Admin AddPeer by rpc caller
     *
     * @param httpServletRequest
     * @param url
     * @return
     */
    public ApiResponse addPeer(HttpServletRequest httpServletRequest,String url) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Admin.build(targetHost).addPeer(url));
    }


    /**
     * Admin RemovePeer
     *
     * @param httpServletRequest
     * @param url
     * @return
     */
    public ApiResponse removePeer(HttpServletRequest httpServletRequest,String url) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Admin.build(targetHost).removePeer(url));
    }


    /**
     * Admin dataDir by rpc caller
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse dataDir(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Admin.build(targetHost).dataDir());
    }


    /**
     * Admin NodeInfo by rpc caller
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse nodeInfo(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Admin.build(targetHost).nodeInfo());
    }


    /**
     * Admin Peers by rpc caller
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse peers(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Admin.build(targetHost).peers());
    }


    /**
     * Admin Start Rpc by rpc caller
     *
     * @param httpServletRequest
     * @param host
     * @param port
     * @param cors
     * @param apis
     * @return
     */
    public ApiResponse startRPC(HttpServletRequest httpServletRequest,String host,int port,String cors,String[] apis) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Admin.build(targetHost).startRPC(host,port,cors,apis));
    }


    /**
     * Admin Start Ws by rpc caller
     *
     * @param httpServletRequest
     * @param host
     * @param port
     * @param cors
     * @param apis
     * @return
     */
    public ApiResponse startWS(HttpServletRequest httpServletRequest,String host,int port,String cors,String[] apis) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Admin.build(targetHost).startWS(host,port,cors,apis));
    }


    /**
     * Admin Stop Rpc by rpc caller
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse stopRPC(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Admin.build(targetHost).stopRPC());
    }


    /**
     * Admin Stop Ws by rpc caller
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse stopWS(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Admin.build(targetHost).stopWS());
    }


    /**
     * Admin import chain
     *
     * @param httpServletRequest
     * @param fileName
     * @return
     */
    public ApiResponse importChain(HttpServletRequest httpServletRequest,String fileName) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Admin.build(targetHost).importChain(fileName));
    }


    /**
     * Admin export chain
     *
     * @param httpServletRequest
     * @param fileName
     * @return
     */
    public ApiResponse exportChain(HttpServletRequest httpServletRequest,String fileName) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Admin.build(targetHost).exportChain(fileName));
    }
}

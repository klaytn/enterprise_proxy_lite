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
package com.klaytn.enterpriseproxy.api.platform.service;

import com.klaytn.enterpriseproxy.api.common.model.ApiResponse;
import com.klaytn.enterpriseproxy.api.common.util.ApiUtils;
import com.klaytn.enterpriseproxy.rpc.common.config.RpcProperties;
import com.klaytn.enterpriseproxy.rpc.platform.net.Net;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;


@Service
public class NetService {
    @Autowired
    private RpcProperties rpc;

    

    /**
     * network id
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse networkID(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Net.build(targetHost).networkID());
    }


    /**
     * listening
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse listening(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Net.build(targetHost).listening());
    }


    /**
     * peer count
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse peerCount(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Net.build(targetHost).peerCount());
    }

    /**
     * peer count by type
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse peerCountByType(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Net.build(targetHost).peerCountByType());
    }
}
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
package com.klaytn.enterpriseproxy.api.common.rpc;

import com.klaytn.enterpriseproxy.api.common.model.ApiResponse;
import com.klaytn.enterpriseproxy.api.common.model.ApiResponseCode;
import com.klaytn.enterpriseproxy.api.common.util.ApiUtils;
import com.klaytn.enterpriseproxy.utils.ArrayUtil;
import com.klaytn.enterpriseproxy.utils.JsonUtil;
import com.klaytn.enterpriseproxy.utils.ObjectUtil;
import com.klaytn.enterpriseproxy.utils.StringUtil;
import net.sf.json.JSONObject;
import com.klaytn.enterpriseproxy.rpc.common.config.RpcProperties;
import com.klaytn.enterpriseproxy.rpc.rpcmodules.RpcModules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;


@Component
public class RpcHandler {
    private static final Logger logger = LoggerFactory.getLogger(RpcHandler.class);

    /**
     * Enabling APIS
     * @private
     */
    private static final String[] addCompareUriList = {"admin","debug","klay","miner","net","personal","rpc","txpool","web3"};

    
    @Autowired
    private RpcProperties rpc;




    /**
     * rpc modules call
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse rpcModules(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(RpcModules.build(targetHost).rpcModules());
    }


    /**
     * Validate Api Interface
     *
     * @param request
     * @param apiResponse
     * @return
     */
    public ApiResponse validateApiInterface(HttpServletRequest request,ApiResponse apiResponse) {
        String requestURI = request.getRequestURI();
        if (StringUtil.isEmpty(requestURI)) {
            return null;
        }
        
        Object result = apiResponse.getData();
        if (ObjectUtil.isEmpty(result)) {
            return null;
        }

        int code = apiResponse.getCode();
        if (ApiResponseCode.SUCCESS.getStatusCode() != code) {
            return null;
        }


        String compareURI = this._getCompareURI(requestURI);
        logger.info("@@@@ COMPARE URI  :" + compareURI + " @@@@");
        
        if (StringUtil.isEmpty(compareURI)) {
            return null;
        }

        if (!ArrayUtil.contains(addCompareUriList,compareURI)) {
            return null;
        }

        JSONObject rpcModuleJson = JsonUtil.convertJsonObject(result);
        String version = (rpcModuleJson.has(compareURI)) ? rpcModuleJson.getString(compareURI) : "";
        return (StringUtil.isEmpty(version)) ? ApiUtils.onError(ApiResponseCode.DISABLE_APIS,result) : null;
    }


    /**
     * Extracting the required URI information from the RequestURI
     *  - IMPORTANT!!!
     *    Since it is the Controller RequestMapping standard set in EP,
     *    you need to modify the corresponding function when RequestMapping is changed.
     *
     * @param requestURI
     * @return
     */
    private String _getCompareURI(String requestURI) {
        if (StringUtil.isEmpty(requestURI)) {
            return null;
        }
        
        String[] splitRequestUri = StringUtil.split(requestURI,"/");
        if (splitRequestUri.length <= 2) {
            return null;
        }

        return StringUtil.toLowerCase(StringUtil.trimToEmpty(splitRequestUri[1]));
    }
}
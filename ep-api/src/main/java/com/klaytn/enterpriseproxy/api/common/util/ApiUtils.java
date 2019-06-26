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
package com.klaytn.enterpriseproxy.api.common.util;

import com.klaytn.enterpriseproxy.api.common.model.ApiResponse;
import com.klaytn.enterpriseproxy.api.common.model.ApiResponseCode;
import com.klaytn.enterpriseproxy.api.common.model.ApiResponseTarget;
import com.klaytn.enterpriseproxy.rpc.common.config.RpcProperties;
import com.klaytn.enterpriseproxy.rpc.common.model.RpcResponse;
import com.klaytn.enterpriseproxy.tx.model.TransactionResponse;
import com.klaytn.enterpriseproxy.utils.JsonUtil;
import com.klaytn.enterpriseproxy.utils.ObjectUtil;
import com.klaytn.enterpriseproxy.utils.StringUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ApiUtils {
    /**
     * IP ADDRESS REG Pattern
     * @private
     */
    private static final Pattern IP_ADDRESS_PATTERN = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$", Pattern.CASE_INSENSITIVE);


    /**
     * SUCCESS RESPONSE
     *
     * @param data
     * @return
     */
    public static ApiResponse onSuccess(final Object data) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setTarget(ApiResponseTarget.API.getTarget());
        apiResponse.setCode(ApiResponseCode.SUCCESS.getStatusCode());
        apiResponse.setResult(ApiResponseCode.SUCCESS.getMessage());
        apiResponse.setData(JsonUtil.converToGson(data));
        return apiResponse;
    }


    /**
     * SUCCESS RESPONSE
     *
     * @return
     */
    public static ApiResponse onSuccess() {
        return onSuccess(null);
    }


    /**
     * ERROR RESPONSE
     *
     * @param code
     * @return
     */
    public static ApiResponse onError(ApiResponseCode code) {
        return onError(code,"");
    }


    /**
     * ERROR RESPONSE
     *
     * @param apiResponseTarget
     * @param code
     * @param message
     * @return
     */
    public static ApiResponse onError(ApiResponseTarget apiResponseTarget,ApiResponseCode code,Object message) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setTarget(apiResponseTarget.getTarget());
        apiResponse.setCode(code.getStatusCode());
        apiResponse.setResult(code.getMessage());
        apiResponse.setData(message);
        return apiResponse;
    }


    /**
     * ERROR RESPONSE
     *
     * @param code
     * @param message
     * @return
     */
    public static ApiResponse onError(ApiResponseCode code,Object message) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setTarget(ApiResponseTarget.API.getTarget());
        apiResponse.setCode(code.getStatusCode());
        apiResponse.setResult(code.getMessage());
        apiResponse.setData(message);
        return apiResponse;
    }


    /**
     * rpc response mapping
     *
     * @param rpcResponse
     * @return
     */
    public static ApiResponse onRpcResponse(RpcResponse rpcResponse) {
        if (ObjectUtil.isEmpty(rpcResponse)) {
            return onError(
                    ApiResponseTarget.RPC,
                    ApiResponseCode.EMPTY_DATA,
                    ApiResponseCode.EMPTY_DATA.getMessage()
            );
        }

        if (ObjectUtil.isNotEmpty(rpcResponse.getError())) {
            return onError(
                    ApiResponseTarget.RPC,
                    ApiResponseCode.RPC_CALL_ERROR,
                    rpcResponse.getError()
            );
        }

        return onSuccess(rpcResponse.getResult());
    }


    /**
     * transaction response
     *
     * @param response
     * @return
     */
    public static ApiResponse onTransactionResponse(TransactionResponse response) {
        if (response.getCode() != 0) {
            return onError(
                    ApiResponseTarget.TRANSACTION,
                    ApiResponseCode.TX_ERROR,
                    response
            );
        }

        return onSuccess(response.getResult());
    }


    /**
     * fee delegated transaction response
     *
     * @param response
     * @return
     */
    public static ApiResponse onFeeDelegatedResponse(TransactionResponse response) {
        if (response.getCode() != 0) {
            return onError(
                    ApiResponseTarget.FEE,
                    ApiResponseCode.TX_ERROR,
                    response
            );
        }

        return onSuccess(response.getResult());
    }



    /**
     * Check send tx for success
     *
     * @param response
     * @return
     */
    public static boolean isSendTxSuccess(TransactionResponse response) {
        return !ObjectUtil.isEmpty(response) && response.getCode() == 0;
    }


    /**
     * disenable apis interface handling error response when invoked
     *
     * @param httpResponse
     * @param apiResponse
     */
    public static void apisInterfaceErrorResponse(HttpServletResponse httpResponse,ApiResponse apiResponse) {
        _responseWrite(httpResponse,apiResponse);
    }


    /**
     * httpServletResponse PrintWrite
     *
     * @param httpResponse
     * @param apiResponse
     */
    private static void _responseWrite(HttpServletResponse httpResponse,ApiResponse apiResponse) {
        httpResponse.setContentType("application/json;charset=UTF-8");
        httpResponse.setStatus(200);

        try {
            PrintWriter out = httpResponse.getWriter();
            out.print(apiResponse);
            out.flush();
            out.close();
        } catch (Exception e) {}

        return;
    }


    /**
     * get header svrChainHost
     *
     * @param request
     * @return
     */
    public static String getTargetHost(HttpServletRequest request) {
        String targetHost = (String) request.getAttribute("svrChainHost");
        return (StringUtil.isEmpty(targetHost)) ? "" : targetHost;
    }


    /**
     * get header target host
     *
     * @param request
     * @param rpc
     * @return
     */
    public static String getTargetHost(HttpServletRequest request,RpcProperties rpc) {
        String targetHost = getTargetHost(request);
        String rpcHost = rpc.getHost()+":"+rpc.getPort();
        return (StringUtil.isEmpty(targetHost)) ? rpcHost : targetHost;
    }


    /**
     * Get client IP information
     * - Since L4 may misinform IP information, it should be imported using "X-Forwarded-For" header information.
     *
     * @param httpRequest
     * @return
     */
    public static String getIpAddress(HttpServletRequest httpRequest) {
        String ipAddress = httpRequest.getHeader("HTTP_X_FORWARDED_FOR");
        if (ObjectUtil.isEmpty(ipAddress) || ipAddress.toLowerCase().equals("unknown")) {
            ipAddress = httpRequest.getHeader("X-Forwarded-For");
        }
        
        if (ObjectUtil.isEmpty(ipAddress) || ipAddress.toLowerCase().equals("unknown")) {
            ipAddress = httpRequest.getHeader("REMOTE_ADDR");
        }

        if (ObjectUtil.isEmpty(ipAddress) || ipAddress.toLowerCase().equals("unknown")) {
            ipAddress = httpRequest.getRemoteAddr();
        }

        Matcher matcher = IP_ADDRESS_PATTERN.matcher(ipAddress);
        return (matcher.find()) ? ipAddress : "127.0.0.1";
    }


    private ApiUtils() {
        throw new IllegalStateException("Utility Class");
    }
}
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
package com.klaytn.enterpriseproxy.api.fee.service;

import com.klaytn.enterpriseproxy.api.common.model.ApiResponse;
import com.klaytn.enterpriseproxy.api.common.model.ApiResponseCode;
import com.klaytn.enterpriseproxy.api.common.model.ApiResponseTarget;
import com.klaytn.enterpriseproxy.api.common.util.ApiUtils;
import com.klaytn.enterpriseproxy.fee.delegated.Value;
import com.klaytn.enterpriseproxy.fee.model.FeeDelegatedParams;
import com.klaytn.enterpriseproxy.fee.util.FeeUtil;
import com.klaytn.enterpriseproxy.tx.model.TransactionResponse;
import com.klaytn.enterpriseproxy.tx.model.TransactionResponseCode;
import com.klaytn.enterpriseproxy.utils.ObjectUtil;
import com.klaytn.enterpriseproxy.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;


@Service
public class FeeDelegatedValueService {
    @Autowired
    private Value value;




    /**
     * fee delegated value transfer
     *
     * @param request
     * @param chainId
     * @param rawTx
     * @return
     */
    public ApiResponse transfer(HttpServletRequest request,int chainId,String rawTx) {
        if (ObjectUtil.isEmpty(chainId) || StringUtil.isEmpty(rawTx)) {
            return ApiUtils.onError(ApiResponseCode.PARAMETER_INVALID);
        }

        FeeDelegatedParams params = FeeUtil.setFeeDelegatedParams(chainId,rawTx,ApiUtils.getTargetHost(request));
        TransactionResponse response = value.transfer(params);

        int responseCode = response.getCode();
        return (responseCode == TransactionResponseCode.SUCCESS.getCode()) ?
                ApiUtils.onFeeDelegatedResponse(response) :
                ApiUtils.onError(ApiResponseTarget.FEE,ApiResponseCode.TX_ERROR,response);
    }


    /**
     * fee delegated value transger with ratio
     *
     * @param request
     * @param chainId
     * @param rawTx
     * @return
     */
    public ApiResponse transferWithRatio(HttpServletRequest request,int chainId,String rawTx) {
        if (ObjectUtil.isEmpty(chainId) || StringUtil.isEmpty(rawTx)) {
            return ApiUtils.onError(ApiResponseCode.PARAMETER_INVALID);
        }

        FeeDelegatedParams params = FeeUtil.setFeeDelegatedParams(chainId,rawTx,ApiUtils.getTargetHost(request));
        TransactionResponse response = value.transferWithRatio(params);

        int responseCode = response.getCode();
        return (responseCode == TransactionResponseCode.SUCCESS.getCode()) ?
                ApiUtils.onFeeDelegatedResponse(response) :
                ApiUtils.onError(ApiResponseTarget.FEE,ApiResponseCode.TX_ERROR,response);
    }


    /**
     * fee delegated value transfer memo
     *
     * @param request
     * @param chainId
     * @param rawTx
     * @return
     */
    public ApiResponse transferMemo(HttpServletRequest request,int chainId,String rawTx) {
        if (ObjectUtil.isEmpty(chainId) || StringUtil.isEmpty(rawTx)) {
            return ApiUtils.onError(ApiResponseCode.PARAMETER_INVALID);
        }

        FeeDelegatedParams params = FeeUtil.setFeeDelegatedParams(chainId,rawTx,ApiUtils.getTargetHost(request));
        TransactionResponse response = value.transferMemo(params);

        int responseCode = response.getCode();
        return (responseCode == TransactionResponseCode.SUCCESS.getCode()) ?
                ApiUtils.onFeeDelegatedResponse(response) :
                ApiUtils.onError(ApiResponseTarget.FEE,ApiResponseCode.TX_ERROR,response);
    }


    /**
     * fee delegated value transfer memo with ratio
     *
     * @param request
     * @param chainId
     * @param rawTx
     * @return
     */
    public ApiResponse transferMemoWithRatio(HttpServletRequest request,int chainId,String rawTx) {
        if (ObjectUtil.isEmpty(chainId) || StringUtil.isEmpty(rawTx)) {
            return ApiUtils.onError(ApiResponseCode.PARAMETER_INVALID);
        }

        FeeDelegatedParams params = FeeUtil.setFeeDelegatedParams(chainId,rawTx,ApiUtils.getTargetHost(request));
        TransactionResponse response = value.transferMemoWithRatio(params);

        int responseCode = response.getCode();
        return (responseCode == TransactionResponseCode.SUCCESS.getCode()) ?
                ApiUtils.onFeeDelegatedResponse(response) :
                ApiUtils.onError(ApiResponseTarget.FEE,ApiResponseCode.TX_ERROR,response);
    }
}
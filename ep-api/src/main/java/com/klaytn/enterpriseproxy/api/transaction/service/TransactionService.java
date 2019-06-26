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
package com.klaytn.enterpriseproxy.api.transaction.service;

import com.klaytn.enterpriseproxy.api.common.model.ApiResponse;
import com.klaytn.enterpriseproxy.api.common.model.ApiResponseCode;
import com.klaytn.enterpriseproxy.api.common.util.ApiUtils;
import com.klaytn.enterpriseproxy.tx.model.TransactionResponse;
import com.klaytn.enterpriseproxy.tx.transaction.RawTransaction;
import com.klaytn.enterpriseproxy.utils.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;


@Service
public class TransactionService {
    @Autowired
    private RawTransaction rawTransaction;




    /**
     * signed transaction
     *
     * @param request
     * @param rawTx
     * @return
     */
    public ApiResponse signed(HttpServletRequest request,String rawTx) {
        if (ObjectUtil.isEmpty(rawTx)) {
            return ApiUtils.onError(ApiResponseCode.PARAMETER_INVALID);
        }

        // rawtx send transaction
        TransactionResponse response = rawTransaction.send(ApiUtils.getTargetHost(request),rawTx);
        return ApiUtils.onTransactionResponse(response);
    }
}
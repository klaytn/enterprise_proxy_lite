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
package com.klaytn.enterpriseproxy.fee.util;

import com.klaytn.caver.crpyto.KlayCredentials;
import com.klaytn.caver.tx.model.KlayRawTransaction;
import com.klaytn.caver.wallet.KlayWalletUtils;
import com.klaytn.enterpriseproxy.fee.model.FeeDelegatedParams;
import com.klaytn.enterpriseproxy.fee.config.FeePayerProperties;


public class FeeUtil {
    /**
     * Fee Payer Credentials
     *
     * @param privateKey
     * @return
     */
    public static KlayCredentials feePayerCredentials(String privateKey) {
        return KlayCredentials.create(privateKey);
    }


    /**
     * Fee Payer Crendentials
     *
     * @param properties
     * @return
     */
    public static KlayCredentials feePayerCredentials(FeePayerProperties properties) {
        try {
            return KlayWalletUtils.loadCredentials(properties.getPassword(),properties.getKeyStoreFilePath());
        }
        catch (Exception e) {
            return null;
        }
    }


    /**
     * Fee Payer raw tx hash return
     *
     * @param transaction
     * @return
     */
    public static String feePayerRawTxHash(KlayRawTransaction transaction) {
        return transaction.getValueAsString();
    }


    /**
     * set Fee Delegated Params
     *
     * @param chainId
     * @param rawTx
     * @param targetHost
     * @return
     */
    public static FeeDelegatedParams setFeeDelegatedParams(int chainId,String rawTx,String targetHost) {
        FeeDelegatedParams params = new FeeDelegatedParams();
        params.setChainId(chainId);
        params.setRawTransactionHash(rawTx);
        params.setTargetHost(targetHost);
        return params;
    }


    private FeeUtil() {
        throw new IllegalStateException("Utility Class");
    }
}
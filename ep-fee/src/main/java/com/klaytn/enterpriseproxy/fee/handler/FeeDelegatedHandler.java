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
package com.klaytn.enterpriseproxy.fee.handler;

import com.klaytn.caver.Caver;
import com.klaytn.caver.crpyto.KlayCredentials;
import com.klaytn.caver.fee.FeePayerManager;
import com.klaytn.caver.methods.response.KlayTransactionReceipt;
import com.klaytn.caver.wallet.KlayWalletUtils;
import com.klaytn.enterpriseproxy.fee.config.FeePayerProperties;
import com.klaytn.enterpriseproxy.fee.model.FeeDelegatedParams;
import com.klaytn.enterpriseproxy.fee.util.FeeUtil;
import com.klaytn.enterpriseproxy.tx.model.TransactionResponse;
import com.klaytn.enterpriseproxy.tx.model.TransactionResponseCode;
import com.klaytn.enterpriseproxy.tx.util.TxUtil;
import com.klaytn.enterpriseproxy.utils.ObjectUtil;
import com.klaytn.enterpriseproxy.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class FeeDelegatedHandler {
    private static final Logger logger = LoggerFactory.getLogger(FeeDelegatedHandler.class);

    @Autowired
    private FeePayerProperties properties;




    /**
     * FeePayerManager build
     *
     * @param feeDelegatedParams
     * @return
     */
    public FeePayerManager create(FeeDelegatedParams feeDelegatedParams) {
        Caver caver = Caver.build(feeDelegatedParams.getTargetHost());
        KlayCredentials feePayerCredentials = FeeUtil.feePayerCredentials(properties);
        return new FeePayerManager.Builder(caver,feePayerCredentials)
                .setChainId(feeDelegatedParams.getChainId())
                .build();
    }


    /**
     * fee delegated send
     *
     * @param rawTxHash
     * @param feePayerManager
     * @return
     */
    public TransactionResponse exec(String rawTxHash,FeePayerManager feePayerManager) {
        if (ObjectUtil.isEmpty(feePayerManager)) {
            return TxUtil.onError(TransactionResponseCode.FEE_DELEGATED_TX_PARAMETER_ERROR);
        }
        
        KlayTransactionReceipt.TransactionReceipt transactionReceipt;
        
        try {
            transactionReceipt = feePayerManager.executeTransaction(rawTxHash);
        }
        catch (Exception e) {
            logger.error("@@@@ FEE HANDLER ERROR : " + e.getLocalizedMessage() + " @@@@");
            return TxUtil.onError(TransactionResponseCode.FEE_DELEGATED_TX_ERROR,e.getLocalizedMessage());
        }

        String txError = transactionReceipt.getTxError();
        String txErrorMessage = transactionReceipt.getErrorMessage();
        
        if (StringUtil.isNotEmpty(txError) || StringUtil.isNotEmpty(txErrorMessage)) {
            return TxUtil.onError(TransactionResponseCode.FEE_DELEGATED_TX_ERROR,txErrorMessage);
        }

        return TxUtil.onSuccess(transactionReceipt);
    }


    /**
     * fee payer properties information validation
     *
     * @return
     */
    public TransactionResponse feePayerWalletValidation() {
        String feePayerPassword = properties.getPassword();
        String feePayerKeyStoreFilePath = properties.getKeyStoreFilePath();
        if (StringUtil.isEmpty(feePayerPassword) || StringUtil.isEmpty(feePayerKeyStoreFilePath)) {
            return TxUtil.onError(TransactionResponseCode.FEE_PAYER_NEED_TO_CHECK_CONFIGURATION_INFORMATION);
        }

        try {
            KlayWalletUtils.loadCredentials(feePayerPassword,feePayerKeyStoreFilePath);
            return TxUtil.onSuccess();
        }
        catch (Exception e) {
            return TxUtil.onError(TransactionResponseCode.FEE_PAYER_NEED_TO_CHECK_CONFIGURATION_INFORMATION,e.getLocalizedMessage());
        }
    }
}
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
package com.klaytn.enterpriseproxy.fee.delegated;

import com.klaytn.caver.tx.type.*;
import com.klaytn.enterpriseproxy.fee.model.FeeDelegatedParams;
import com.klaytn.enterpriseproxy.tx.model.TransactionResponse;
import com.klaytn.enterpriseproxy.tx.model.TransactionResponseCode;
import com.klaytn.enterpriseproxy.tx.util.TxUtil;
import com.klaytn.enterpriseproxy.utils.ObjectUtil;
import com.klaytn.enterpriseproxy.fee.handler.FeeDelegatedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SmartContract {
    @Autowired
    private FeeDelegatedHandler handler;




    /**
     * Tx Type Fee Delegated Smart Contract Deploy deploys a smart contract
     *
     * @param params
     * @return
     */
    public TransactionResponse deploy(FeeDelegatedParams params) {
        String rawTxHash = params.getRawTransactionHash();
        if (!TxUtil.validateTxHashFormat(rawTxHash)) {
            return TxUtil.onError(TransactionResponseCode.TX_FORMAT_INVALID);
        }

        TxTypeFeeDelegatedSmartContractDeploy tx = TxTypeFeeDelegatedSmartContractDeploy.decodeFromRawTransaction(rawTxHash);
        if (ObjectUtil.isEmpty(tx)) {
            return TxUtil.onError(TransactionResponseCode.TX_FORMAT_INVALID);
        }

        TxType.Type txType = tx.getType();
        if (!TxUtil.isTxTypeEquals(txType,TxType.Type.FEE_DELEGATED_SMART_CONTRACT_DEPLOY)) {
            return TxUtil.onError(TransactionResponseCode.TX_FORMAT_INVALID);
        }

        return handler.exec(rawTxHash,handler.create(params));
    }


    /**
     * Tx Type Fee Delegated Smart Contract Deploy With Ratio deploys a smart contract
     *
     * @param params
     * @return
     */
    public TransactionResponse deployWithRatio(FeeDelegatedParams params) {
        String rawTxHash = params.getRawTransactionHash();
        if (!TxUtil.validateTxHashFormat(rawTxHash)) {
            return TxUtil.onError(TransactionResponseCode.TX_FORMAT_INVALID);
        }

        TxTypeFeeDelegatedSmartContractDeployWithRatio tx = TxTypeFeeDelegatedSmartContractDeployWithRatio.decodeFromRawTransaction(rawTxHash);
        if (ObjectUtil.isEmpty(tx)) {
            return TxUtil.onError(TransactionResponseCode.TX_FORMAT_INVALID);
        }

        TxType.Type txType = tx.getType();
        if (!TxUtil.isTxTypeEquals(txType,TxType.Type.FEE_DELEGATED_SMART_CONTRACT_DEPLOY_WITH_RATIO)) {
            return TxUtil.onError(TransactionResponseCode.TX_FORMAT_INVALID);
        }

        return handler.exec(rawTxHash,handler.create(params));
    }


    /**
     * Tx Type Fee Delegated Smart Contract Execution executes a smart contract with the given data
     *
     * @param params
     * @return
     */
    public TransactionResponse execution(FeeDelegatedParams params) {
        String rawTxHash = params.getRawTransactionHash();
        if (!TxUtil.validateTxHashFormat(rawTxHash)) {
            return TxUtil.onError(TransactionResponseCode.TX_FORMAT_INVALID);
        }

        TxTypeFeeDelegatedSmartContractExecution tx = TxTypeFeeDelegatedSmartContractExecution.decodeFromRawTransaction(rawTxHash);
        if (ObjectUtil.isEmpty(tx)) {
            return TxUtil.onError(TransactionResponseCode.TX_FORMAT_INVALID);
        }

        TxType.Type txType = tx.getType();
        if (!TxUtil.isTxTypeEquals(txType,TxType.Type.FEE_DELEGATED_SMART_CONTRACT_EXECUTION)) {
            return TxUtil.onError(TransactionResponseCode.TX_FORMAT_INVALID);
        }

        return handler.exec(rawTxHash,handler.create(params));
    }


    /**
     * Tx Type Fee Delegated Smart Contract Execution With Ratio executes a smart contract with the given data
     *
     * @param params
     * @return
     */
    public TransactionResponse executionWithRatio(FeeDelegatedParams params) {
        String rawTxHash = params.getRawTransactionHash();
        if (!TxUtil.validateTxHashFormat(rawTxHash)) {
            return TxUtil.onError(TransactionResponseCode.TX_FORMAT_INVALID);
        }

        TxTypeFeeDelegatedSmartContractExecutionWithRatio tx = TxTypeFeeDelegatedSmartContractExecutionWithRatio.decodeFromRawTransaction(rawTxHash);
        if (ObjectUtil.isEmpty(tx)) {
            return TxUtil.onError(TransactionResponseCode.TX_FORMAT_INVALID);
        }

        TxType.Type txType = tx.getType();
        if (!TxUtil.isTxTypeEquals(txType,TxType.Type.FEE_DELEGATED_SMART_CONTRACT_EXECUTION_WITH_RATIO)) {
            return TxUtil.onError(TransactionResponseCode.TX_FORMAT_INVALID);
        }

        return handler.exec(rawTxHash,handler.create(params));
    }
}
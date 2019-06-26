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
public class Value {
    @Autowired
    private FeeDelegatedHandler handler;



    /**
     * Tx Type Fee Delegated Value Transfer is a value transfer transaction with a fee payer.
     *
     * @param params
     * @return
     */
    public TransactionResponse transfer(FeeDelegatedParams params) {
        String rawTxHash = params.getRawTransactionHash();
        if (!TxUtil.validateTxHashFormat(rawTxHash)) {
            return TxUtil.onError(TransactionResponseCode.TX_PARAMETER_INVALID);
        }

        TxTypeFeeDelegatedValueTransfer tx = TxTypeFeeDelegatedValueTransfer.decodeFromRawTransaction(rawTxHash);
        if (ObjectUtil.isEmpty(tx)) {
            return TxUtil.onError(TransactionResponseCode.TX_FORMAT_INVALID);
        }

        TxType.Type txType = tx.getType();
        if (!TxUtil.isTxTypeEquals(txType,TxType.Type.FEE_DELEGATED_VALUE_TRANSFER)) {
            return TxUtil.onError(TransactionResponseCode.TX_TYPE_INVALID);
        }

        return handler.exec(rawTxHash,handler.create(params));
    }


    /**
     * Tx Type Fee Delegated Value Transfer With Ratio is a value transfer with a fee payer and its ratio
     *
     * @param params
     * @return
     */
    public TransactionResponse transferWithRatio(FeeDelegatedParams params) {
        String rawTxHash = params.getRawTransactionHash();
        if (!TxUtil.validateTxHashFormat(rawTxHash)) {
            return TxUtil.onError(TransactionResponseCode.TX_PARAMETER_INVALID);
        }

        TxTypeFeeDelegatedValueTransferWithRatio tx = TxTypeFeeDelegatedValueTransferWithRatio.decodeFromRawTransaction(rawTxHash);
        if (ObjectUtil.isEmpty(tx)) {
            return TxUtil.onError(TransactionResponseCode.TX_FORMAT_INVALID);
        }

        TxType.Type txType = tx.getType();
        if (!TxUtil.isTxTypeEquals(txType,TxType.Type.FEE_DELEGATED_VALUE_TRANSFER_WITH_RATIO)) {
            return TxUtil.onError(TransactionResponseCode.TX_TYPE_INVALID);
        }

        return handler.exec(rawTxHash,handler.create(params));
    }


    /**
     * Tx Type Fee Delegated Value Transfer Memo Transfers KLAY with a data
     *
     * @param params
     * @return
     */
    public TransactionResponse transferMemo(FeeDelegatedParams params) {
        String rawTxHash = params.getRawTransactionHash();
        if (!TxUtil.validateTxHashFormat(rawTxHash)) {
            return TxUtil.onError(TransactionResponseCode.TX_PARAMETER_INVALID);
        }

        TxTypeFeeDelegatedValueTransferMemo tx = TxTypeFeeDelegatedValueTransferMemo.decodeFromRawTransaction(rawTxHash);
        if (ObjectUtil.isEmpty(tx)) {
            return TxUtil.onError(TransactionResponseCode.TX_FORMAT_INVALID);
        }

        TxType.Type txType = tx.getType();
        if (!TxUtil.isTxTypeEquals(txType,TxType.Type.FEE_DELEGATED_VALUE_TRANSFER_MEMO)) {
            return TxUtil.onError(TransactionResponseCode.TX_TYPE_INVALID);
        }

        return handler.exec(rawTxHash,handler.create(params));
    }


    /**
     * Tx Type Fee Delegated Value Transfer Memo With Ratio transfer KLAY with a data
     *
     * @param params
     * @return
     */
    public TransactionResponse transferMemoWithRatio(FeeDelegatedParams params) {
        String rawTxHash = params.getRawTransactionHash();
        if (!TxUtil.validateTxHashFormat(rawTxHash)) {
            return TxUtil.onError(TransactionResponseCode.TX_PARAMETER_INVALID);
        }

        TxTypeFeeDelegatedValueTransferMemoWithRatio tx = TxTypeFeeDelegatedValueTransferMemoWithRatio.decodeFromRawTransaction(rawTxHash);
        if (ObjectUtil.isEmpty(tx)) {
            return TxUtil.onError(TransactionResponseCode.TX_FORMAT_INVALID);
        }

        TxType.Type txType = tx.getType();
        if (!TxUtil.isTxTypeEquals(txType,TxType.Type.FEE_DELEGATED_VALUE_TRANSFER_MEMO_WITH_RATIO)) {
            return TxUtil.onError(TransactionResponseCode.TX_TYPE_INVALID);
        }

        return handler.exec(rawTxHash,handler.create(params));
    }
}
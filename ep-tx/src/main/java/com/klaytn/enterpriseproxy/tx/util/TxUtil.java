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
package com.klaytn.enterpriseproxy.tx.util;

import com.klaytn.caver.Caver;
import com.klaytn.caver.tx.type.TxType;
import com.klaytn.enterpriseproxy.utils.JsonUtil;
import com.klaytn.enterpriseproxy.utils.ObjectUtil;
import com.klaytn.enterpriseproxy.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import com.klaytn.enterpriseproxy.tx.model.TransactionResponse;
import com.klaytn.enterpriseproxy.tx.model.TransactionResponseCode;
import org.web3j.utils.Numeric;


public class TxUtil {
    /**
     * transaction send success response
     *
     * @return
     */
    public static TransactionResponse onSuccess() {
        return onSuccess(null);
    }



    /**
     * transaction send success response
     *
     * @param object
     * @return
     */
    public static TransactionResponse onSuccess(final Object object) {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setCode(TransactionResponseCode.SUCCESS.getCode());
        transactionResponse.setResult(TransactionResponseCode.SUCCESS.getMessage());
        transactionResponse.setData(JsonUtil.converToGson(object));
        return transactionResponse;
    }


    /**
     * transaction send error response
     *
     * @param code
     * @return
     */
    public static TransactionResponse onError(final TransactionResponseCode code) {
        return onError(code, code.getMessage());
    }


    /**
     * transaction send error response
     *
     * @param code
     * @param message
     * @return
     */
    public static TransactionResponse onError(final TransactionResponseCode code, String message) {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setCode(code.getCode());
        transactionResponse.setResult(message);
        return transactionResponse;
    }


    /**
     * rawTxHash is validate
     *
     * @param txHash
     * @return
     */
    public static boolean validateTxHashFormat(String txHash) {
        if (ObjectUtil.isEmpty(txHash)) {
            return false;
        }

        if (Numeric.containsHexPrefix(txHash) == false) {
            return false;
        }

        String hexTypeStr = StringUtils.substring(txHash,0,4);
        if (ObjectUtil.isEmpty(hexTypeStr)) {
            return false;
        }

        byte[] txTypeBytes = Numeric.hexStringToByteArray(hexTypeStr);
        if (txTypeBytes.length == 0) {
            return false;
        }

        byte txTypeByte = txTypeBytes[0];
        if (txTypeByte == 0x00) {
            return false;
        }

        return true;
    }


    /**
     * TxType is KlayType validate
     *
     * @param txType
     * @return
     */
    public static boolean isKlayTxType(byte txType) {
        return (TxType.Type.LEGACY.equals(TxType.Type.findByValue(txType))) ? false : true;
    }


    /**
     * TxType equals check
     *
     * @param senderType
     * @param typeCheck
     * @return
     */
    public static boolean isTxTypeEquals(TxType.Type senderType, TxType.Type typeCheck) {
        return (typeCheck.equals(senderType));
    }


    /**
     * caverj build
     *
     * @param targetHost
     * @return
     */
    public static Caver caverBuild(String targetHost) {
        return (StringUtil.isEmpty(targetHost)) ? Caver.build(Caver.BAOBAB_URL) : Caver.build(targetHost);
    }

    
    private TxUtil() {
        throw new IllegalStateException("Utility Class");
    }
}
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
package com.klaytn.enterpriseproxy.tx.transaction;

import com.klaytn.caver.methods.request.KlayTransaction;
import com.klaytn.enterpriseproxy.tx.config.TxProperties;
import com.klaytn.enterpriseproxy.tx.transfer.Transfer;
import com.klaytn.enterpriseproxy.utils.StringUtil;
import com.klaytn.enterpriseproxy.tx.exception.TransactionException;
import com.klaytn.enterpriseproxy.tx.model.TransactionResponse;
import com.klaytn.enterpriseproxy.tx.model.TransactionResponseCode;
import com.klaytn.enterpriseproxy.tx.util.TxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.Response;

import javax.annotation.PostConstruct;


@Component
public class RawTransaction {
    private static final Logger logger = LoggerFactory.getLogger(RawTransaction.class);
    
    @Autowired
    private TxProperties properties;


    /**
     * chain host
     * @type string
     */
    private String propertiesHost;




    @PostConstruct
    public void init() {
        this.propertiesHost = properties.getHost() + ":" + properties.getPort();
    }


    /**
     * signed transaction send by rawTxHash
     *
     * @param targetHost
     * @param rawTxHash
     * @return
     */
    public TransactionResponse send(String targetHost,String rawTxHash) {
        if (!TxUtil.validateTxHashFormat(rawTxHash)) {
            return TxUtil.onError(TransactionResponseCode.TX_FORMAT_INVALID);
        }

        try {
            Response<String> transaction = Transfer.sendTransaction(this._getTargetHost(targetHost),rawTxHash);
            if (transaction.hasError()) {
                return TxUtil.onError(
                        TransactionResponseCode.SIGNED_TX_ERROR,
                        transaction.getError().getMessage()
                );
            }

            logger.info("@@@@ RAW TX HASH TRANSACTION RESULT : " + transaction.getResult() + " @@@@");
            return TxUtil.onSuccess(transaction.getResult());
        }
        catch (TransactionException e) {
            return TxUtil.onError(TransactionResponseCode.SIGNED_TX_ERROR,e.getLocalizedMessage());
        }
    }


    /**
     * signed transaction send by KlayTransaction
     *
     * @param targetHost
     * @param klayTransaction
     * @return
     */
    public TransactionResponse send(String targetHost,KlayTransaction klayTransaction) {
        try {
            Response<String> transaction = Transfer.sendTransaction(this._getTargetHost(targetHost),klayTransaction);
            if (transaction.hasError()) {
                return TxUtil.onError(
                        TransactionResponseCode.SIGNED_TX_ERROR,
                        transaction.getError().getMessage()
                );
            }

            logger.info("@@@@ TRANSACTION RESULT : " + transaction.getResult() + " @@@@");
            return TxUtil.onSuccess(transaction.getResult());
        }
        catch (TransactionException e) {
            return TxUtil.onError(TransactionResponseCode.SIGNED_TX_ERROR,e.getLocalizedMessage());
        }
    }


    /**
     * host 정보 가져오기
     *
     * @param targetHost
     * @return
     */
    private String _getTargetHost(String targetHost) {
        return (StringUtil.isEmpty(targetHost)) ? this.propertiesHost : targetHost;
    }
}
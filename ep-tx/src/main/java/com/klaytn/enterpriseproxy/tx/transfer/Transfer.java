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
package com.klaytn.enterpriseproxy.tx.transfer;

import com.klaytn.caver.Caver;
import com.klaytn.caver.methods.request.KlayTransaction;
import com.klaytn.enterpriseproxy.tx.exception.TransactionException;
import com.klaytn.enterpriseproxy.tx.model.TransactionResponseCode;
import org.web3j.protocol.core.Response;


public class Transfer {
    /**
     * send transaction
     *
     * @param host
     * @param rawtx
     * @param async
     * @return
     */
    public static Response<String> sendTransaction(String host,String rawtx,boolean async) throws TransactionException {
        return (async) ? sendAsync(host,rawtx) : send(host,rawtx);
    }


    /**
     * send transaction
     *
     * @param host
     * @param klayTransaction
     * @param async
     * @return
     * @throws TransactionException
     */
    public static Response<String> sendTransaction(String host,KlayTransaction klayTransaction,boolean async) throws TransactionException {
        return (async) ? sendAsync(host,klayTransaction) : send(host,klayTransaction);
    }


    /**
     * send transaction
     *
     * @param host
     * @param rawtx
     * @return
     * @throws TransactionException
     */
    public static Response<String> sendTransaction(String host,String rawtx) throws TransactionException {
        return sendTransaction(host,rawtx,true);
    }


    /**
     * send transaction
     *
     * @param host
     * @param klayTransaction
     * @return
     * @throws TransactionException
     */
    public static Response<String> sendTransaction(String host,KlayTransaction klayTransaction) throws TransactionException {
        return sendTransaction(host,klayTransaction,true);
    }


    /**
     * send async signed transcation
     *
     * @param host
     * @param rawtx
     * @return
     * @throws TransactionException
     */
    private static Response<String> sendAsync(String host,String rawtx) throws TransactionException {
        try {
            return Caver.build(host).klay()
                    .sendSignedTransaction(rawtx)
                    .sendAsync()
                    .get();
        }

        catch (Exception e) {
            throw new TransactionException(TransactionResponseCode.TX_ERROR,e);
        }
    }


    /**
     * klay send async klay transaction
     *
     * @param host
     * @param klayTransaction
     * @return
     * @throws TransactionException
     */
    private static Response<String> sendAsync(String host,KlayTransaction klayTransaction) throws TransactionException {
        try {
            return Caver.build(host).klay()
                    .sendTransaction(klayTransaction)
                    .sendAsync()
                    .get();
        }

        catch (Exception e) {
            throw new TransactionException(TransactionResponseCode.TX_ERROR,e);
        }
    }


    /**
     * send signed transcation
     *
     * @param host
     * @param rawtx
     * @return
     * @throws TransactionException
     */
    private static Response<String> send(String host,String rawtx) throws TransactionException {
        try {
            return Caver.build(host).klay()
                    .sendSignedTransaction(rawtx)
                    .send();
        }

        catch (Exception e) {
            throw new TransactionException(TransactionResponseCode.TX_ERROR,e);
        }
    }


    /**
     * klay send transaction
     *
     * @param host
     * @param klayTransaction
     * @return
     * @throws TransactionException
     */
    private static Response<String> send(String host,KlayTransaction klayTransaction) throws TransactionException {
        try {
            return Caver.build(host).klay()
                    .sendTransaction(klayTransaction)
                    .send();
        }

        catch (Exception e) {
            throw new TransactionException(TransactionResponseCode.TX_ERROR,e);
        }
    }
}
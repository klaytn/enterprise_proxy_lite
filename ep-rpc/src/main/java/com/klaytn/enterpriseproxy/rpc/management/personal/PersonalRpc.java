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
package com.klaytn.enterpriseproxy.rpc.management.personal;

import com.klaytn.enterpriseproxy.rpc.common.model.RpcMethods;
import com.klaytn.enterpriseproxy.rpc.common.model.RpcResponse;
import com.klaytn.enterpriseproxy.rpc.common.transfer.RpcTransfer;
import com.klaytn.enterpriseproxy.rpc.common.util.RpcUtils;
import com.klaytn.enterpriseproxy.utils.ObjectUtil;
import com.klaytn.enterpriseproxy.utils.StringUtil;


public class PersonalRpc implements Personal {
    private RpcTransfer transfer;




    public PersonalRpc(String targetHost) {
        this.transfer = new RpcTransfer(targetHost);
    }


    /**
     * Imports the given unencrypted private key (hex string) into the key store, encrypting it with the passphrase.
     * Returns the address of the new account
     *
     * @param keyData
     * @param passPhrase
     * @return
     */
    @Override
    public RpcResponse importRawKey(String keyData,String passPhrase) {
        if (StringUtil.isEmpty(keyData) || StringUtil.isEmpty(passPhrase)) {
            return new RpcResponse();
        }

        String[] params = {keyData, passPhrase};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.PERSONAL_IMPORTRAWKEY,params));
    }


    /**
     * Returns all the Klaytn account addresses of all keys in the key store
     *
     * @return
     */
    @Override
    public RpcResponse listAccounts() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.PERSONAL_LISTACCOUNTS));
    }


    /**
     * Removes the private key with a given address from memory.
     * The account can no longer be used to send transactions
     *
     * @param accountAddress
     * @return
     */
    @Override
    public RpcResponse lockAccount(String accountAddress) {
        if (ObjectUtil.isEmpty(accountAddress)) {
            return new RpcResponse();
        }
        
        String[] params = {accountAddress};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.PERSONAL_LOCKACCOUNT,params));
    }


    /**
     * Decrypts the key with the given address from the key store
     *
     * The unencrypted key will be held in memory until the unlock duration expires.
     * If the unlock duration defaults to 300 seconds.
     * An explicit duration of zero seconds unlocks the key until the Klaytn local node exits.
     *
     * The account can be used with klay_sign and klay_sendTransaction while it is unlocked
     *
     * @param accountAddress
     * @param passPhrase
     * @param duration
     * @return
     */
    @Override
    public RpcResponse unlockAccount(String accountAddress, String passPhrase, int duration) {
        if (ObjectUtil.isEmpty(accountAddress) || ObjectUtil.isEmpty(passPhrase)) {
            return new RpcResponse();
        }
        
        Object[] params = {accountAddress, passPhrase, duration};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.PERSONAL_UNLOCKACCOUNT,params));
    }


    /**
     * he transaction is the same argument as for klay_sendTransaction and contains the from address.
     * If the passphrase can be used to decrypt the private key belonging to tx.from the transaction is verified,
     * signed and send onto the network.
     *
     * The account is not unlocked globally in the node and cannot be used in other RPC calls.
     *
     * @param fromAddress
     * @param toAddress
     * @param gas
     * @param gasPrice
     * @param value
     * @param data
     * @param nonce
     * @param passPhrase
     * @return
     */
    @Override
    public RpcResponse sendTransaction(String fromAddress,String toAddress,String gas,String gasPrice,String value,String data,String nonce,String passPhrase) {
        if (ObjectUtil.isEmpty(fromAddress) || ObjectUtil.isEmpty(passPhrase)) {
            return new RpcResponse();
        }

        Object[] params = {RpcUtils.setKlayCall(fromAddress,toAddress,gas,gasPrice,value,data,nonce), passPhrase};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.PERSONAL_SENDTRANSACTION,params));
    }


    /**
     * The sign method calculates a Klaytn-specific signature with:
     *
     *      sign(keccak256("\x19Klaytn Signed Message:\n" + len(message) + message)))
     *
     * Adding a prefix to the message makes the calculated signature recognizable as a Klaytn-specific signature.
     * This prevents misuse where a malicious DApp can sign arbitrary data (e.g., transaction) and use the signature to impersonate the victim.
     *
     * @param message
     * @param accountAddress
     * @param passPhrase
     * @return
     */
    @Override
    public RpcResponse sign(String message,String accountAddress,String passPhrase) {
        if (ObjectUtil.isEmpty(message) || ObjectUtil.isEmpty(accountAddress)) {
            return new RpcResponse();
        }

        Object[] params = {message,accountAddress,passPhrase};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.PERSONAL_SIGN,params));
    }


    /**
     * ecRecover returns the address associated with the private key that was used to calculate the signature in personal_sign
     *
     * @param message
     * @param signature
     * @return
     */
    @Override
    public RpcResponse ecRecover(String message,String signature) {
        if (ObjectUtil.isEmpty(message) || ObjectUtil.isEmpty(signature)) {
            return new RpcResponse();
        }
        
        Object[] params = {message,signature};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.PERSONAL_ECRECOVER,params));
    }
}

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

import com.klaytn.enterpriseproxy.rpc.common.model.RpcResponse;


public interface Personal {
    static Personal build(String targetHost) {
        return new PersonalRpc(targetHost);
    }



    
    RpcResponse importRawKey(String keyData,String passPhrase);

    RpcResponse listAccounts();

    RpcResponse lockAccount(String accountAddress);

    RpcResponse unlockAccount(String accountAddress, String passPhrase, int duration);

    RpcResponse sendTransaction(String fromAddress, String toAddress, String gas, String gasPrice, String value, String data, String nonce, String passPhrase);

    RpcResponse sign(String message, String accountAddress, String passPhrase);

    RpcResponse ecRecover(String message, String signature);
}

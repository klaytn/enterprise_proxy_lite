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
package com.klaytn.enterpriseproxy.rpc.management.txpool;

import com.klaytn.enterpriseproxy.rpc.common.model.RpcMethods;
import com.klaytn.enterpriseproxy.rpc.common.model.RpcResponse;
import com.klaytn.enterpriseproxy.rpc.common.transfer.RpcTransfer;
import com.klaytn.enterpriseproxy.rpc.common.util.RpcUtils;


public class TxPoolRpc implements TxPool {
    private RpcTransfer transfer;




    public TxPoolRpc(String targetHost) {
        this.transfer = new RpcTransfer(targetHost);
    }

    
    /**
     * The content inspection property can be queried to list the exact details of all the transactions currently
     * pending for inclusion in the next block(s), as well as the ones that are being scheduled for future execution only
     *
     * @return
     */
    public RpcResponse content() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.TXPOOL_CONTENT));
    }


    /**
     * The inspect inspection property can be queried to list a textual summary of all the transactions currently
     * pending for inclusion in the next block(s), as well as the ones that are being scheduled for future execution only
     *
     * @return
     */
    public RpcResponse inspect() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.TXPOOL_INSPECT));
    }


    /**
     * The status inspection property can be queried for the number of transactions currently
     * pending for inclusion in the next block(s), as well as the ones that are being scheduled for future execution only
     *
     * @return
     */
    public RpcResponse status() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.TXPOOL_STATUS));
    }
}
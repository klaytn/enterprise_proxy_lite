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
package com.klaytn.enterpriseproxy.rpc.platform.web3;

import com.klaytn.enterpriseproxy.rpc.common.model.RpcMethods;
import com.klaytn.enterpriseproxy.rpc.common.model.RpcResponse;
import com.klaytn.enterpriseproxy.rpc.common.transfer.RpcTransfer;
import com.klaytn.enterpriseproxy.rpc.common.util.RpcUtils;
import com.klaytn.enterpriseproxy.utils.ObjectUtil;


public class Web3Rpc implements Web3 {
    /**
     * json rpc transfer class
     * @type class
     */
    private RpcTransfer transfer;




    public Web3Rpc(String targetHost) {
        this.transfer = new RpcTransfer(targetHost);
    }

    
    /**
     * Returns the current client version.
     *
     * @return
     */
    @Deprecated
    public RpcResponse clientVersion() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.WEB3_CLIENTVERSION));
    }


    /**
     * Returns Keccak-256 (not the standardized SHA3-256) of the given data.
     *
     * @param sha3hash
     * @return
     */
    @Deprecated
    public RpcResponse sha3(String sha3hash) {
        if (ObjectUtil.isEmpty(sha3hash)) {
            return new RpcResponse();
        }
        
        String[] params = {sha3hash};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.WEB3_SHA3,params));
    }
}
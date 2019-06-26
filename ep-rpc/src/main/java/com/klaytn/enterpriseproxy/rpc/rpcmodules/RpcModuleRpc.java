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
package com.klaytn.enterpriseproxy.rpc.rpcmodules;

import com.klaytn.enterpriseproxy.rpc.common.model.RpcMethods;
import com.klaytn.enterpriseproxy.rpc.common.model.RpcResponse;
import com.klaytn.enterpriseproxy.rpc.common.transfer.RpcTransfer;
import com.klaytn.enterpriseproxy.rpc.common.util.RpcUtils;


public class RpcModuleRpc implements RpcModules {
    private RpcTransfer transfer;




    public RpcModuleRpc(String targetHost) {
        this.transfer = new RpcTransfer(targetHost);
    }


    /**
     * will give all enabled modules including the version number
     *
     * @return
     */
    public RpcResponse rpcModules() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.RPC_MODULES));
    }
}
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
package com.klaytn.enterpriseproxy.rpc.platform.net;

import com.klaytn.enterpriseproxy.rpc.common.model.RpcMethods;
import com.klaytn.enterpriseproxy.rpc.common.model.RpcResponse;
import com.klaytn.enterpriseproxy.rpc.common.transfer.RpcTransfer;
import com.klaytn.enterpriseproxy.rpc.common.util.RpcUtils;


public class NetRpc implements Net {
    /**
     * json rpc transfer class
     * @type class
     */
    private RpcTransfer transfer;




    public NetRpc(String targetHost) {
        this.transfer = new RpcTransfer(targetHost);
    }


    /**
     * Returns the network identifier (network ID)
     *
     * @return
     */
    @Override
    public RpcResponse networkID() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.NET_NETWORKID));
    }


    /**
     * Returns true if the client is actively listening for network connections.
     *
     * @return
     */
    @Override
    public RpcResponse listening() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.NET_LISTENING));
    }


    /**
     * Returns the number of peers currently connected to the client.
     *
     * @return
     */
    @Override
    public RpcResponse peerCount() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.NET_PEERCOUNT));
    }


    /**
     * Returns the number of connected nodes by type and the total number of connected nodes with key/value pairs.
     *
     * @return
     */
    @Override
    public RpcResponse peerCountByType() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.NET_PEERCOUNTBYTYPE));
    }
}
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
package com.klaytn.enterpriseproxy.rpc.management.admin;

import com.klaytn.enterpriseproxy.rpc.common.model.RpcResponse;


public interface Admin {
    static Admin build(String targetHost) {
        return new AdminRpc(targetHost);
    }



    
    RpcResponse dataDir();

    RpcResponse addPeer(String url);

    RpcResponse removePeer(String url);

    RpcResponse nodeInfo();

    RpcResponse peers();

    RpcResponse startRPC(String host,int port,String cors,String[] apis);

    RpcResponse stopRPC();

    RpcResponse startWS(String host,int port,String cors,String[] apis);

    RpcResponse stopWS();

    RpcResponse importChain(String fileName);
    
    RpcResponse exportChain(String fileName);
}

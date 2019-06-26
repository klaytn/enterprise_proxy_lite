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

import com.klaytn.enterpriseproxy.rpc.common.model.RpcMethods;
import com.klaytn.enterpriseproxy.rpc.common.model.RpcResponse;
import com.klaytn.enterpriseproxy.rpc.common.transfer.RpcTransfer;
import com.klaytn.enterpriseproxy.rpc.common.util.RpcUtils;
import com.klaytn.enterpriseproxy.utils.MapUtil;
import com.klaytn.enterpriseproxy.utils.ObjectUtil;
import com.klaytn.enterpriseproxy.utils.StringUtil;

import java.util.Map;


public class AdminRpc implements Admin {
    private RpcTransfer transfer;



    
    public AdminRpc(String targetHost) {
        this.transfer = new RpcTransfer(targetHost);
    }

    
    /**
     * The datadir administrative property can be queried for the absolute path the running Klaytn node currently uses to store all its databases
     *
     * @return
     */
    public RpcResponse dataDir() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.ADMIN_DATADIR));
    }


    /**
     * The addPeer administrative method requests adding a new remote node to the list of tracked static nodes
     *
     * @param url
     * @return
     */
    public RpcResponse addPeer(String url) {
        if (ObjectUtil.isEmpty(url)) {
            return new RpcResponse();
        }
        
        Object[] params = {url};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.ADMIN_ADDPEER,params));
    }


    /**
     * The removePeer administrative method requests removing a node from the list of tracked static nodes
     *
     * @param url
     * @return
     */
    public RpcResponse removePeer(String url) {
        if (StringUtil.isEmpty(url)) {
            return new RpcResponse();
        }

        Object[] params = {url};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.ADMIN_REMOVEPEER,params));
    }


    /**
     * The nodeInfo administrative property can be queried for all the information known about the running Klaytn node at the networking granularity
     *
     * @return
     */
    public RpcResponse nodeInfo() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.ADMIN_NODEINFO));
    }


    /**
     * The peers administrative property can be queried for all the information known about the connected remote nodes at the networking granularity
     *
     * @return
     */
    public RpcResponse peers() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.ADMIN_PEERS));
    }


    /**
     * The startRPC administrative method starts an HTTP based JSON RPC API webserver to handle client requests
     *
     * @param host
     * @param port
     * @param cors
     * @param apis
     * @return
     */
    public RpcResponse startRPC(String host,int port,String cors,String[] apis) {
        host = (StringUtil.isEmpty(host)) ? "localhost" : host;
        port = (port <= 0) ? 8551  : port;

        Object[] params = {host,port,cors,apis};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.ADMIN_STARTRPC,params));
    }


    /**
     * The stopRPC administrative method closes the currently open HTTP RPC endpoint
     *
     * @return
     */
    public RpcResponse stopRPC() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.ADMIN_STOPRPC));
    }


    /**
     * The startWS administrative method starts an WebSocket based JSON RPC API webserver to handle client requests.
     *
     * @param host
     * @param port
     * @param cors
     * @param apis
     * @return
     */
    public RpcResponse startWS(String host,int port,String cors,String[] apis) {
        host = (StringUtil.isEmpty(host)) ? "localhost" : host;
        port = (port <= 0) ? 8552 : port;

        Object[] params = {host,port,cors,apis};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.ADMIN_STARTWS,params));
    }


    /**
     * The stopWS administrative method closes the currently open WebSocket RPC endpoint
     *
     * @return
     */
    public RpcResponse stopWS() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.ADMIN_STOPWS));
    }


    /**
     * The importChain administrative method imports an exported chain from file into node
     *
     * @param fileName
     * @return
     */
    public RpcResponse importChain(String fileName) {
        if (StringUtil.isEmpty(fileName)) {
            return new RpcResponse();
        }

        String[] params = {fileName};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.ADMIN_IMPORTCHAIN,params));
    }


    /**
     * The exportChain administrative method exports the blockchain to a file
     *
     * @param fileName
     * @return
     */
    public RpcResponse exportChain(String fileName) {
        if (StringUtil.isEmpty(fileName)) {
            return new RpcResponse();
        }

        String[] params = {fileName};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.ADMIN_EXPORTCHAIN,params));
    }
}

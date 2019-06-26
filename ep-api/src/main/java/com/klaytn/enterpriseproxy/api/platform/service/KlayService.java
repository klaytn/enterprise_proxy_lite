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
package com.klaytn.enterpriseproxy.api.platform.service;

import com.klaytn.enterpriseproxy.api.common.model.ApiResponse;
import com.klaytn.enterpriseproxy.api.common.util.ApiUtils;
import com.klaytn.enterpriseproxy.rpc.common.config.RpcProperties;
import com.klaytn.enterpriseproxy.rpc.platform.klay.Klay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;


@Service
public class KlayService {
    @Autowired
    private RpcProperties rpc;



    
    /**
     * protocol version
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse protocolVersion(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).protocolVersion());
    }


    /**
     * syncing
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse syncing(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).syncing());
    }


    /**
     * mining
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse mining(HttpServletRequest httpServletRequest)  {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).mining());
    }


    /**
     * gas price
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse gasPrice(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).gasPrice());
    }


    /**
     * gas price at
     *
     * @param httpServletRequest
     * @param blockParameter
     * @return
     */
    public ApiResponse gasPriceAt(HttpServletRequest httpServletRequest,String blockParameter) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).gasPriceAt(blockParameter));
    }


    /**
     * accounts
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse accounts(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).accounts());
    }


    /**
     * get account
     *
     * @param httpServletRequest
     * @param accountAddress
     * @param blockParameter
     * @return
     */
    public ApiResponse getAccount(HttpServletRequest httpServletRequest, String accountAddress, String blockParameter) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getAccount(accountAddress, blockParameter));
    }


    /**
     * get account key
     *
     * @param httpServletRequest
     * @param accountAddress
     * @param blockParameter
     * @return
     */
    public ApiResponse getAccountKey(HttpServletRequest httpServletRequest, String accountAddress, String blockParameter) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getAccountKey(accountAddress, blockParameter));
    }


    /**
     * Klay Account Created
     *
     * @param httpServletRequest
     * @param accountAddress
     * @param blockParameter
     * @return
     */
    public ApiResponse accountCreated(HttpServletRequest httpServletRequest,String accountAddress,String blockParameter) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).accountCreated(accountAddress,blockParameter));
    }


    /**
     * Klay is Contract Account
     *
     * @param httpServletRequest
     * @param accountAddress
     * @param blockParameter
     * @return
     */
    public ApiResponse isContractAccount(HttpServletRequest httpServletRequest,String accountAddress,String blockParameter) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).isContractAccount(accountAddress,blockParameter));
    }


    /**
     * block number
     *
     * @return
     */
    public ApiResponse blockNumber(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).blockNumber());
    }


    /**
     * get balance
     *
     * @param address
     * @param blockParameter
     * @return
     */
    public ApiResponse getBalance(HttpServletRequest httpServletRequest,String address,String blockParameter) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getBalance(address,blockParameter));
    }


    /**
     * get storage at
     *
     * @param httpServletRequest
     * @param contractAddress
     * @param position
     * @param blockParameter
     * @return
     */
    public ApiResponse getStorageAt(HttpServletRequest httpServletRequest,String contractAddress,String position,String blockParameter) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getStorageAt(contractAddress,position,blockParameter));
    }


    /**
     * get transaction count
     *
     * @param address
     * @param blockParameter
     * @return
     */
    public ApiResponse getTransactionCount(HttpServletRequest httpServletRequest,String address,String blockParameter) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getTransactionCount(address,blockParameter));
    }


    /**
     * get block transaction count by hash
     *
     * @param httpServletRequest
     * @param blockHash
     * @return
     */
    public ApiResponse getBlockTransactionCountByHash(HttpServletRequest httpServletRequest,String blockHash) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getBlockTransactionCountByHash(blockHash));
    }


    /**
     * get block transaction count by number
     *
     * @param httpServletRequest
     * @param blockParameter
     * @return
     */
    public ApiResponse getBlockTransactionCountByNumber(HttpServletRequest httpServletRequest,String blockParameter) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getBlockTransactionCountByNumber(blockParameter));
    }


    /**
     * get code
     *
     * @param httpServletRequest
     * @param address
     * @param blockParameter
     * @return
     */
    public ApiResponse getCode(HttpServletRequest httpServletRequest,String address,String blockParameter) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getCode(address,blockParameter));
    }


    /**
     * sign
     *
     * @param httpServletRequest
     * @param address
     * @param message
     * @return
     */
    public ApiResponse sign(HttpServletRequest httpServletRequest,String address,String message) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).sign(address,message));
    }


    /**
     * send transaction
     *
     * @param httpServletRequest
     * @param fromAddress
     * @param toAddress
     * @param gas
     * @param gasPrice
     * @param value
     * @param data
     * @param nonce
     * @return
     */
    public ApiResponse sendTransaction(HttpServletRequest httpServletRequest,String fromAddress,String toAddress,String gas,String gasPrice,String value,String data,String nonce) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).sendTransaction(fromAddress,toAddress,gas,gasPrice,value,data,nonce));
    }


    /**
     * send raw transaction
     *
     * @param httpServletRequest
     * @param transaction
     * @return
     */
    public ApiResponse sendRawTransaction(HttpServletRequest httpServletRequest,String transaction) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).sendRawTransaction(transaction));
    }


    /**
     * call
     *
     * @param httpServletRequest
     * @param fromAddress
     * @param toAddress
     * @param gas
     * @param gasPrice
     * @param value
     * @param data
     * @param blockParameter
     * @return
     */
    public ApiResponse call(HttpServletRequest httpServletRequest,String fromAddress,String toAddress,String gas,String gasPrice,String value,String data,String blockParameter) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).call(fromAddress,toAddress,gas,gasPrice,value,data,blockParameter));
    }


    /**
     * estimate gas
     *
     * @param httpServletRequest
     * @param fromAddress
     * @param toAddress
     * @param gas
     * @param gasPrice
     * @param value
     * @param data
     * @param blockParameter
     * @return
     */
    public ApiResponse estimateGas(HttpServletRequest httpServletRequest,String fromAddress,String toAddress,String gas,String gasPrice,String value,String data,String blockParameter) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).estimateGas(fromAddress,toAddress,gas,gasPrice,value,data,blockParameter));
    }


    /**
     * get block by hash
     *
     * @param httpServletRequest
     * @param blockHash
     * @param returnTx
     * @return
     */
    public ApiResponse getBlockByHash(HttpServletRequest httpServletRequest,String blockHash,boolean returnTx) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getBlockByHash(blockHash,returnTx));
    }


    /**
     * get block number
     *
     * @param httpServletRequest
     * @param blockParameter
     * @param returnTx
     * @return
     */
    public ApiResponse getBlockByNumber(HttpServletRequest httpServletRequest,String blockParameter,boolean returnTx) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getBlockByNumber(blockParameter,returnTx));
    }


    /**
     * get transaction by hash
     *
     * @param httpServletRequest
     * @param transactionHash
     * @return
     */
    public ApiResponse getTransactionByHash(HttpServletRequest httpServletRequest,String transactionHash) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getTransactionByHash(transactionHash));
    }


    /**
     * get transaction by sender tx hash
     *
     * @param httpServletRequest
     * @param transactionHash
     * @return
     */
    public ApiResponse getTransactionBySenderTxHash(HttpServletRequest httpServletRequest,String transactionHash) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getTransactionBySenderTxHash(transactionHash));
    }

    /**
     * get transaction by block hash and index
     *
     * @param httpServletRequest
     * @param blockHash
     * @param position
     * @return
     */
    public ApiResponse getTransactionByBlockHashAndIndex(HttpServletRequest httpServletRequest,String blockHash,String position) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getTransactionByBlockHashAndIndex(blockHash,position));
    }


    /**
     * get transcation by block number and index
     *
     * @param httpServletRequest
     * @param blockParameter
     * @param position
     * @return
     */
    public ApiResponse getTransactionByBlockNumberAndIndex(HttpServletRequest httpServletRequest,String blockParameter,String position) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getTransactionByBlockNumberAndIndex(blockParameter,position));
    }


    /**
     * get transaction receipt
     *
     * @param httpServletRequest
     * @param transactionHash
     * @return
     */
    public ApiResponse getTransactionReceipt(HttpServletRequest httpServletRequest,String transactionHash) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getTransactionReceipt(transactionHash));
    }

    /**
     * get transaction receipt by sender tx hash
     *
     * @param httpServletRequest
     * @param transactionHash
     * @return
     */
    public ApiResponse getTransactionReceiptBySenderTxHash(HttpServletRequest httpServletRequest,String transactionHash) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getTransactionReceiptBySenderTxHash(transactionHash));
    }


    /**
     * new filter
     *
     * @param httpServletRequest
     * @param fromBlock
     * @param toBlock
     * @param addresses
     * @param topics
     * @return
     */
    public ApiResponse newFilter(HttpServletRequest httpServletRequest,String fromBlock,String toBlock,String[] addresses,String[] topics) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).newFilter(fromBlock,toBlock,addresses,topics));
    }


    /**
     * new block filter
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse newBlockFilter(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).newBlockFilter());
    }


    /**
     * new pending transaction filter
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse newPendingTransactionFilter(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).newPendingTransactionFilter());
    }


    /**
     * uninstall filter
     *
     * @param httpServletRequest
     * @param filter
     * @return
     */
    public ApiResponse uninstallFilter(HttpServletRequest httpServletRequest,String filter) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).uninstallFilter(filter));
    }


    /**
     * get filter changes
     *
     * @param httpServletRequest
     * @param filterId
     * @return
     */
    public ApiResponse getFilterChanges(HttpServletRequest httpServletRequest,String filterId) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getFilterChanges(filterId));
    }


    /**
     * get filter logs
     *
     * @param httpServletRequest
     * @param filterId
     * @return
     */
    public ApiResponse getFilterLogs(HttpServletRequest httpServletRequest,String filterId) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getFilterLogs(filterId));
    }


    /**
     * get logs
     *
     * @param httpServletRequest
     * @param fromBlock
     * @param toBlock
     * @param addresses
     * @param topics
     * @param blockHash
     * @return
     */
    public ApiResponse getLogs(HttpServletRequest httpServletRequest,String fromBlock,String toBlock,String[] addresses,String[] topics,String blockHash) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getLogs(fromBlock,toBlock,addresses,topics,blockHash));
    }


    /**
     * get work
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse getWork(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getWork());
    }


    /**
     * get validators
     *
     * @param httpServletRequest
     * @param blockParameter
     * @return
     */
    @Deprecated
    public ApiResponse getValidators(HttpServletRequest httpServletRequest,String blockParameter) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getValidators(blockParameter));
    }


    /**
     * get block with consensus info by hash
     *
     * @param httpServletRequest
     * @param blockHash
     * @return
     */
    public ApiResponse getBlockWithConsensusInfoByHash(HttpServletRequest httpServletRequest,String blockHash) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getBlockWithConsensusInfoByHash(blockHash));
    }


    /**
     * get block with consensus info by number
     *
     * @param httpServletRequest
     * @param blockNumber
     * @return
     */
    public ApiResponse getBlockWithConsensusInfoByNumber(HttpServletRequest httpServletRequest,String blockNumber) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getBlockWithConsensusInfoByNumber(blockNumber));
    }


    /**
     * get committee
     *
     * @param httpServletRequest
     * @param blockNumber
     * @return
     */
    public ApiResponse getCommittee(HttpServletRequest httpServletRequest,String blockNumber) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getCommittee(blockNumber));
    }


    /**
     * get committee size
     *
     * @param httpServletRequest
     * @param blockNumber
     * @return
     */
    public ApiResponse getCommitteeSize(HttpServletRequest httpServletRequest,String blockNumber) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getCommitteeSize(blockNumber));
    }


    /**
     * get council
     *
     * @param httpServletRequest
     * @param blockNumber
     * @return
     */
    public ApiResponse getCouncil(HttpServletRequest httpServletRequest,String blockNumber) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getCouncil(blockNumber));
    }


    /**
     * get council size
     *
     * @param httpServletRequest
     * @param blockNumber
     * @return
     */
    public ApiResponse getCouncilSize(HttpServletRequest httpServletRequest,String blockNumber) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getCouncilSize(blockNumber));
    }


    /**
     * chain id
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse chainID(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).chainID());
    }


    /**
     * client version
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse clientVersion(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).clientVersion());
    }


    /**
     * rewardbase
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse rewardbase(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).rewardbase());
    }


    /**
     * is parallel db write
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse isParallelDBWrite(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).isParallelDBWrite());
    }

    /**
     * is sender tx hash indexing enabled
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse isSenderTxHashIndexingEnabled(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).isSenderTxHashIndexingEnabled());
    }


    /**
     * write through caching
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse writeThroughCaching(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).writeThroughCaching());
    }


    /**
     * sign transaction
     *
     * @param httpServletRequest
     * @param fromAddress
     * @param toAddress
     * @param gas
     * @param gasPrice
     * @param value
     * @param data
     * @param nonce
     * @return
     */
    public ApiResponse signTransaction(HttpServletRequest httpServletRequest,String fromAddress,String toAddress,String gas,String gasPrice,String value,String data,String nonce) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).signTransaction(fromAddress,toAddress,gas,gasPrice,value,data,nonce));
    }


    /**
     * get block receipts
     *
     * @param httpServletRequest
     * @param blockHash
     * @return
     */
    public ApiResponse getBlockReceipts(HttpServletRequest httpServletRequest,String blockHash) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).getBlockReceipts(blockHash));
    }

    /**
     * sha3
     *
     * @param httpServletRequest
     * @param data
     * @return
     */
    public ApiResponse sha3(HttpServletRequest httpServletRequest,String data) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Klay.build(targetHost).sha3(data));
    }
}

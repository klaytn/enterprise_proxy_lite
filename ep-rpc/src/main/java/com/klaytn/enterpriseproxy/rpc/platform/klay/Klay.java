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
package com.klaytn.enterpriseproxy.rpc.platform.klay;

import com.klaytn.enterpriseproxy.rpc.common.model.RpcResponse;


public interface Klay {
    static Klay build(String targetHost) {
        return new KlayRpc(targetHost);
    }




    RpcResponse protocolVersion();

    RpcResponse syncing();

    RpcResponse mining();

    RpcResponse gasPrice();

    RpcResponse gasPriceAt(String blockParameter);

    RpcResponse accounts();

    RpcResponse getAccount(String accountAddress, String blockParameter);

    RpcResponse getAccountKey(String accountAddress, String blockParameter);

    RpcResponse accountCreated(String acountAddress,String blockParameter);

    RpcResponse isContractAccount(String accountAddress,String blockParameter);

    RpcResponse blockNumber();

    RpcResponse getBalance(String address, String blockParameter);

    RpcResponse getStorageAt(String contractAddress, String position, String blockParameter);

    RpcResponse getTransactionCount(String accountAddress, String blockParameter);

    RpcResponse getBlockTransactionCountByHash(String blockHash);

    RpcResponse getBlockTransactionCountByNumber(String blockParameter);

    RpcResponse getCode(String address, String blockParameter);

    RpcResponse sign(String accountAddress, String message);

    RpcResponse sendTransaction(String fromAddress,String toAddress,String gas,String gasPrice,String value,String data,String nonce);

    RpcResponse sendRawTransaction(String transaction);

    RpcResponse call(String fromAddress, String toAddress, String gas, String gasPrice, String value, String data,String blockParameter);

    RpcResponse estimateGas(String fromAddress,String toAddress,String gas,String gasPrice,String value,String data,String blockParameter);

    RpcResponse getBlockByHash(String blockHash, boolean returnTx);

    RpcResponse getBlockByNumber(String blockParameter, boolean returnTx);

    RpcResponse getTransactionByHash(String transactionHash);

    RpcResponse getTransactionBySenderTxHash(String transactionHash);

    RpcResponse getTransactionByBlockHashAndIndex(String blockHash, String position);

    RpcResponse getTransactionByBlockNumberAndIndex(String blockParameter, String position);

    RpcResponse getTransactionReceipt(String transactionHash);

    RpcResponse getTransactionReceiptBySenderTxHash(String transactionHash);

    RpcResponse newFilter(String fromBlock, String toBlock, String[] address, String[] topics);

    RpcResponse newBlockFilter();

    RpcResponse newPendingTransactionFilter();

    RpcResponse uninstallFilter(String filter);

    RpcResponse getFilterChanges(String filterId);

    RpcResponse getFilterLogs(String filterId);

    RpcResponse getLogs(String fromBlock, String toBlock, String[] address, String[] topics, String blockHash);

    RpcResponse getWork();

    RpcResponse getBlockWithConsensusInfoByHash(String blockHash);

    RpcResponse getBlockWithConsensusInfoByNumber(String blockParameter);

    RpcResponse getCommittee(String blockParameter);

    RpcResponse getCommitteeSize(String blockParameter);

    RpcResponse getCouncil(String blockParameter);

    RpcResponse getCouncilSize(String blockParameter);

    RpcResponse chainID();

    RpcResponse clientVersion();

    RpcResponse rewardbase();

    RpcResponse isParallelDBWrite();

    RpcResponse isSenderTxHashIndexingEnabled();

    RpcResponse writeThroughCaching();

    RpcResponse signTransaction(String from,String to,String gas,String gasPrice,String value,String data,String nonce);

    RpcResponse getBlockReceipts(String blockHash);

    RpcResponse sha3(String data);

    @Deprecated
    RpcResponse getValidators(String blockParameter);
}

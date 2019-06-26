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

import com.klaytn.enterpriseproxy.rpc.common.model.RpcMethods;
import com.klaytn.enterpriseproxy.rpc.common.model.RpcResponse;
import com.klaytn.enterpriseproxy.rpc.common.transfer.RpcTransfer;
import com.klaytn.enterpriseproxy.rpc.common.util.RpcUtils;
import com.klaytn.enterpriseproxy.utils.ObjectUtil;
import com.klaytn.enterpriseproxy.utils.StringUtil;


public class KlayRpc implements Klay {
    private RpcTransfer transfer;




    public KlayRpc(String targetHost) {
        this.transfer = new RpcTransfer(targetHost);
    }


    /**
     * Returns the Klaytn protocol version of the node
     *
     * @return
     */
    @Override
    public RpcResponse protocolVersion() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_PROTOCOLVERSION));
    }


    /**
     * Returns an object with data about the sync status or false
     *
     * @return
     */
    @Override
    public RpcResponse syncing() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_SYNCING));
    }


    /**
     * Returns true if client is actively mining new blocks.
     *
     * @return
     */
    @Override
    public RpcResponse mining() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_MINING));
    }


    /**
     * Returns the current price per gas in peb.
     *
     * @return
     */
    @Override
    public RpcResponse gasPrice() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GASPRICE));
    }


    /**
     * Returns the unit price of the given block in peb.
     *
     * @return
     */
    @Override
    public RpcResponse gasPriceAt(String blockParameter) {
        if (StringUtil.isEmpty(blockParameter)) {
            return new RpcResponse();
        }

        String[] params = {blockParameter};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GASPRICEAT,params));
    }


    /**
     * Returns a list of addresses owned by client.
     *
     * @return
     */
    @Override
    public RpcResponse accounts() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_ACCOUNTS));
    }


    /**
     * Returns the account information of a given address.
     * There are two different account types in Klaytn: Externally Owned Account (EOA) and Smart Contract Account.
     *
     * @return
     */
    @Override
    public RpcResponse getAccount(String accountAddress, String blockParameter) {
        if (StringUtil.isEmpty(accountAddress) || ObjectUtil.isEmpty(blockParameter)) {
            return new RpcResponse();
        }

        Object[] params = {accountAddress, blockParameter};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETACCOUNT,params));
    }


    /**
     * Returns the account information of a given address.
     * There are two different account types in Klaytn: Externally Owned Account (EOA) and Smart Contract Account.
     *
     * @return
     */
    @Override
    public RpcResponse getAccountKey(String accountAddress, String blockParameter) {
        if (StringUtil.isEmpty(accountAddress) || ObjectUtil.isEmpty(blockParameter)) {
            return new RpcResponse();
        }

        Object[] params = {accountAddress, blockParameter};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETACCOUNTKEY,params));
    }

    /**
     * Returns true if the account associated with the address is created. It returns false otherwise
     *
     * @param accountAddress
     * @param blockParameter
     * @return
     */
    @Override
    public RpcResponse accountCreated(String accountAddress,String blockParameter) {
        if (StringUtil.isEmpty(accountAddress)) {
            return new RpcResponse();
        }

        Object[] params = {accountAddress,blockParameter};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_ACCOUNTCREATED,params));
    }


    /**
     * Returns true if an input account has a non-empty codeHash at the time of a specific block number.
     * It returns false if the account is an EOA or a smart contract account which doesn't have codeHash
     *
     * @param accountAddress
     * @param blockParameter
     * @return
     */
    @Override
    public RpcResponse isContractAccount(String accountAddress,String blockParameter) {
        if (StringUtil.isEmpty(accountAddress) || ObjectUtil.isEmpty(blockParameter)) {
            return new RpcResponse();
        }

        Object[] params = {accountAddress, blockParameter};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_ISCONTRACTADDRESS,params));
    }


    /**
     * Returns the number of most recent block
     *
     * @return
     */
    @Override
    public RpcResponse blockNumber() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_BLOCKNUMBER));
    }


    /**
     * Returns the balance of the account of given address
     *
     * @param address
     * @param blockParameter
     * @return
     */
    @Override
    public RpcResponse getBalance(String address, String blockParameter) {
        if (StringUtil.isEmpty(address) || ObjectUtil.isEmpty(blockParameter)) {
            return new RpcResponse();
        }

        Object[] params = {address, blockParameter};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETBALANCE, params));
    }


    /**
     * Returns the value from a storage position at a given address
     *
     * @param contractAddress
     * @param position
     * @param blockParameter
     * @return
     */
    @Override
    public RpcResponse getStorageAt(String contractAddress, String position, String blockParameter) {
        if (StringUtil.isEmpty(contractAddress) || ObjectUtil.isEmpty(position) || ObjectUtil.isEmpty(blockParameter)) {
            return new RpcResponse();
        }

        Object[] params = {contractAddress, position, blockParameter};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETSTORAGEAT, params));
    }


    /**
     * Returns the number of transactions sent from an address
     *
     * @param accountAddress
     * @param blockParameter
     * @return
     */
    @Override
    public RpcResponse getTransactionCount(String accountAddress, String blockParameter) {
        if (StringUtil.isEmpty(accountAddress)) {
            return new RpcResponse();
        }

        Object[] params = {accountAddress, blockParameter};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETTRANSACTIONCOUNT, params));
    }


    /**
     * Returns the number of transactions in a block from a block matching the given block hash
     *
     * @param blockHash
     * @return
     */
    @Override
    public RpcResponse getBlockTransactionCountByHash(String blockHash) {
        if (StringUtil.isEmpty(blockHash)) {
            return new RpcResponse();
        }

        String[] params = {blockHash};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETTRANSACTIONCOUNTBYHASH, params));
    }


    /**
     * Returns the number of transactions in a block matching the given block number
     *
     * @param blockParameter
     * @return
     */
    @Override
    public RpcResponse getBlockTransactionCountByNumber(String blockParameter) {
        if (StringUtil.isEmpty(blockParameter)) {
            return new RpcResponse();
        }
        
        Object[] params = {blockParameter};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETTRANSACTIONCOUNTBYNUMBER, params));
    }


    /**
     * Returns code at a given address
     *
     * @param address
     * @param blockParameter
     * @return
     */
    @Override
    public RpcResponse getCode(String address, String blockParameter) {
        if (StringUtil.isEmpty(address) || StringUtil.isEmpty(blockParameter)) {
            return new RpcResponse();
        }

        Object[] params = {address, blockParameter};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETCODE,params));
    }


    /**
     * The sign method calculates a Klaytn-specific signature with:
     * 
     *      sign(keccak256("\x19Klaytn Signed Message:\n" + len(message) + message)))
     *
     * Adding a prefix to the message makes the calculated signature recognizable as a Klaytn-specific signature.
     * This prevents misuse where a malicious DApp can sign arbitrary data, e.g., transaction, and use the signature to impersonate the victim
     *
     * @param accountAddress
     * @param message
     * @return
     */
    @Override
    public RpcResponse sign(String accountAddress, String message) {
        if (StringUtil.isEmpty(accountAddress) || StringUtil.isEmpty(message)) {
            return new RpcResponse();
        }
        
        String[] params = {accountAddress, message};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_SIGN,params));
    }


    /**
     * Creates a new message call transaction or a contract creation if the data field contains code
     *
     * @param fromAddress
     * @param toAddress
     * @param gas
     * @param gasPrice
     * @param value
     * @param data
     * @param nonce
     * @return
     */
    @Override
    public RpcResponse sendTransaction(String fromAddress,String toAddress,String gas,String gasPrice,String value,String data,String nonce) {
        if (StringUtil.isEmpty(fromAddress)) {
            return new RpcResponse();
        }

        Object[] params = {RpcUtils.setKlayCall(fromAddress,toAddress,gas,gasPrice,value,data,nonce)};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_SENDTRANSACTION,params));
    }


    /**
     * Creates a new message call transaction or a contract creation for signed transactions
     *
     * @param transaction
     * @return
     */
    @Override
    public RpcResponse sendRawTransaction(String transaction) {
        if (StringUtil.isEmpty(transaction)) {
            return new RpcResponse();
        }

        String[] params = {transaction};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_SENDRAWTRANSACTION,params));
    }


    /**
     * Executes a new message call immediately without creating a transaction on the block chain.
     * It returns data or an error object of JSON RPC if error occurs
     *
     * @param fromAddress
     * @param toAddress
     * @param gas
     * @param gasPrice
     * @param value
     * @param data
     * @param blockParameter
     * @return
     */
    @Override
    public RpcResponse call(String fromAddress, String toAddress, String gas, String gasPrice, String value, String data,String blockParameter) {
        if (StringUtil.isEmpty(toAddress) || StringUtil.isEmpty(data) || StringUtil.isEmpty(blockParameter)) {
            return new RpcResponse();
        }

        Object[] params = {RpcUtils.setKlayCall(fromAddress,toAddress,gas,gasPrice,value,data),blockParameter};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_CALL,params));
    }


    /**
     * Generates and returns an estimate of how much gas is necessary to allow the transaction to complete.
     * The transaction will not be added to the blockchain.
     * Note that the estimate may be significantly more than the amount of gas actually used by the transaction,
     * for a variety of reasons including Klaytn Virtual Machine mechanics and node performance
     *
     * @param fromAddress
     * @param toAddress
     * @param gas
     * @param gasPrice
     * @param value
     * @param data
     * @param blockParameter
     * @return
     */
    @Override
    public RpcResponse estimateGas(String fromAddress,String toAddress,String gas,String gasPrice,String value,String data,String blockParameter) {
        Object[] params = {RpcUtils.setKlayCall(fromAddress,toAddress,gas,gasPrice,value,data)};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_ESTIMATEGAS,params));
    }


    /**
     * Returns information about a block by hash
     *
     * @param blockHash
     * @param returnTx
     * @return
     */
    @Override
    public RpcResponse getBlockByHash(String blockHash, boolean returnTx) {
        if (StringUtil.isEmpty(blockHash)) {
            return new RpcResponse();
        }
        
        Object[] params = {blockHash, returnTx};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETBLOAKBYHASH,params));
    }


    /**
     * Returns information about a block by block number
     *
     * @param blockParameter
     * @param returnTx
     * @return
     */
    @Override
    public RpcResponse getBlockByNumber(String blockParameter, boolean returnTx) {
        if (ObjectUtil.isEmpty(blockParameter)) {
            return new RpcResponse();
        }

        Object[] params = {blockParameter, returnTx};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETBLOCKBYNUMBER,params));
    }


    /**
     * Returns the information about a transaction requested by transaction hash
     *
     * @param transactionHash
     * @return
     */
    @Override
    public RpcResponse getTransactionByHash(String transactionHash) {
        if (StringUtil.isEmpty(transactionHash)) {
            return new RpcResponse();
        }
        
        String[] params = {transactionHash};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETTRANSACTIONBYHASH,params));
    }

    /**
     * Returns the information about a transaction requested by sender transaction hash.
     * This API works only on RPC call, not on Javascript console.
     * Please note that this API returns correct result only if indexing feature is enabled by --sendertxhashindexing.
     *
     * @param transactionHash
     * @return
     */
    @Override
    public RpcResponse getTransactionBySenderTxHash(String transactionHash) {
        if (StringUtil.isEmpty(transactionHash)) {
            return new RpcResponse();
        }

        String[] params = {transactionHash};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETTRANSACTIONBYSENDERTXHASH,params));
    }

    /**
     * Returns information about a transaction by block hash and transaction index position
     *
     * @param blockHash
     * @param position
     * @return
     */
    @Override
    public RpcResponse getTransactionByBlockHashAndIndex(String blockHash, String position) {
        if (StringUtil.isEmpty(blockHash) || StringUtil.isEmpty(position)) {
            return new RpcResponse();
        }

        Object[] params = {blockHash, position};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETTRANSACTIONBYBLOCKHASHANDINDEX,params));
    }


    /**
     * Returns information about a transaction by block number and transaction index position
     *
     * @param blockParameter
     * @param position
     * @return
     */
    @Override
    public RpcResponse getTransactionByBlockNumberAndIndex(String blockParameter, String position) {
        if (StringUtil.isEmpty(blockParameter) || StringUtil.isEmpty(position)) {
            return new RpcResponse();
        }

        Object[] params = {blockParameter, position};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETTRANSACTIONBYBLOCKNUMBERANDINDEX,params));
    }


    /**
     * Returns the receipt of a transaction by transaction hash
     *
     * @param transactionHash
     * @return
     */
    @Override
    public RpcResponse getTransactionReceipt(String transactionHash) {
        if (ObjectUtil.isEmpty(transactionHash)) {
            return new RpcResponse();
        }
        
        String[] params = {transactionHash};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETTRANSACTIONRECEIPT,params));
    }


    /**
     * Returns the receipt of a transaction by sender transaction hash.
     * The receipt is not available for pending transactions.
     * Please note that this API returns correct result only if indexing feature is enabled by --sendertxhashindexing
     *
     * @param transactionHash
     * @return
     */
    @Override
    public RpcResponse getTransactionReceiptBySenderTxHash(String transactionHash) {
        if (ObjectUtil.isEmpty(transactionHash)) {
            return new RpcResponse();
        }

        String[] params = {transactionHash};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETTRANSACTIONRECEIPTBYSENDERTXHASH,params));
    }


    /**
     * Creates a filter object, based on filter options, to notify when the state changes 
     *
     * @param fromBlock
     * @param toBlock
     * @param address
     * @param topics
     * @return
     */
    @Override
    public RpcResponse newFilter(String fromBlock, String toBlock, String[] address, String[] topics) {
        Object[] params = {RpcUtils.setFilterOptions(fromBlock,toBlock,address,topics)};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_NEWFILTER,params));
    }


    /**
     * Creates a filter in the node, to notify when a new block arrives
     *
     * @return
     */
    @Override
    public RpcResponse newBlockFilter() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_NEWBLOCKFILTER));
    }


    /**
     * Creates a filter in the node, to notify when new pending transactions arrive
     *
     * @return
     */
    @Override
    public RpcResponse newPendingTransactionFilter() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_NEWPENDINGTRANSACTIONFILTER));
    }


    /**
     * Uninstalls a filter with given id. Should always be called when watch is no longer needed
     *
     * @param filter
     * @return
     */
    @Override
    public RpcResponse uninstallFilter(String filter) {
        if (ObjectUtil.isEmpty(filter)) {
            return new RpcResponse();
        }
        
        String[] params = {filter};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_UNINSTALLFILTER,params));
    }


    /**
     * Polling method for a filter, which returns an array of logs which occurred since last poll
     *
     * @param filterId
     * @return
     */
    @Override
    public RpcResponse getFilterChanges(String filterId) {
        if (ObjectUtil.isEmpty(filterId)) {
            return new RpcResponse();
        }
        
        String[] params = {filterId};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETFILTERCHANGES,params));
    }


    /**
     * Returns an array of all logs matching filter with given id, which has been obtained using klay_newFilter.
     *
     * @param filterId
     * @return
     */
    @Override
    public RpcResponse getFilterLogs(String filterId) {
        if (ObjectUtil.isEmpty(filterId)) {
            return new RpcResponse();
        }
        
        String[] params = {filterId};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETFILTERLOGS,params));
    }


    /**
     * Returns an array of all logs matching a given filter object
     *
     * @param fromBlock
     * @param toBlock
     * @param address
     * @param topics
     * @param blockHash
     * @return
     */
    @Override
    public RpcResponse getLogs(String fromBlock, String toBlock, String[] address, String[] topics, String blockHash) {
        Object[] params = {RpcUtils.setFilterOptions(fromBlock,toBlock,address,topics,blockHash)};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETLOGS,params));
    }


    /**
     * Returns the hash of the current block, the seedHash, and the boundary condition to be met ("target")
     *
     * @return
     */
    @Override
    public RpcResponse getWork() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETWORK));
    }


    /**
     * Returns a block with consensus information matched by the given hash
     *
     * @param blockHash
     * @return
     */
    @Override
    public RpcResponse getBlockWithConsensusInfoByHash(String blockHash) {
        if (ObjectUtil.isEmpty(blockHash)) {
            return new RpcResponse();
        }
        
        String[] params = {blockHash};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETBLOCKWITHCONSENSUSINFOBYHASH,params));
    }


    /**
     * Returns a block with consensus information matched by the given block number
     *
     * @param blockParameter
     * @return
     */
    @Override
    public RpcResponse getBlockWithConsensusInfoByNumber(String blockParameter) {
        if (ObjectUtil.isEmpty(blockParameter)) {
            return new RpcResponse();
        }
        
        Object[] params = {blockParameter};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETBLOCKWITHCONSENSUSINFOBYNUMBER,params));
    }

    /**
     * Returns a list of all validators in the committee at the specified block.
     * If the parameter is not set, returns a list of all validators in the committee at the latest block.
     *
     * @param blockParameter
     * @return
     */
    @Override
    public RpcResponse getCommittee(String blockParameter) {
        if (ObjectUtil.isEmpty(blockParameter)) {
            return new RpcResponse();
        }

        Object[] params = {blockParameter};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETCOMMITTEE,params));
    }


    /**
     * Returns a list of all validators in the committee at the specified block.
     * If the parameter is not set, returns a list of all validators in the committee at the latest block.
     *
     * @param blockParameter
     * @return
     */
    @Override
    public RpcResponse getCommitteeSize(String blockParameter) {
        if (ObjectUtil.isEmpty(blockParameter)) {
            return new RpcResponse();
        }

        Object[] params = {blockParameter};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETCOMMITTEESIZE,params));
    }

    /**
     * Returns a list of all validators of the council at the specified block.
     * If the parameter is not set, returns a list of all validators of the council at the latest block.
     *
     * @param blockParameter
     * @return
     */
    @Override
    public RpcResponse getCouncil(String blockParameter) {
        if (ObjectUtil.isEmpty(blockParameter)) {
            return new RpcResponse();
        }

        Object[] params = {blockParameter};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETCOUNCIL,params));
    }


    /**
     * Returns the size of the council at the specified block.
     * If the parameter is not set, returns the size of the council at the latest block.
     *
     * @param blockParameter
     * @return
     */
    @Override
    public RpcResponse getCouncilSize(String blockParameter) {
        if (ObjectUtil.isEmpty(blockParameter)) {
            return new RpcResponse();
        }

        Object[] params = {blockParameter};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETCOUNCILSIZE,params));
    }


    /**
     * Returns the chain ID of the chain
     *
     * @return
     */
    @Override
    public RpcResponse chainID() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_CHAINID));
    }

    /**
     * Returns the current client version of a Klaytn node.
     *
     * @return
     */
    @Override
    public RpcResponse clientVersion() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_CLIENTVERSION));
    }


    /**
     * Returns the rewardbase of the current node.
     * Rewardbase is the address of the account where the block rewards goes to. It is only required for CNs.
     *
     * @return
     */
    @Override
    public RpcResponse rewardbase() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_REWARDBASE));
    }


    /**
     * Returns true if the node is writing blockchain data in parallel manner.
     * It is enabled by default
     *
     * @return
     */
    @Override
    public RpcResponse isParallelDBWrite() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_ISPARALLELDBWRITE));
    }

    /**
     * Returns true if the node is indexing sender transaction hash to transaction hash mapping information.
     * It is disabled by default and can be enabled by --sendertxhashindexing.
     *
     * @return
     */
    @Override
    public RpcResponse isSenderTxHashIndexingEnabled() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_ISSENDERTXHASHINDEXINGENABLED));
    }


    /**
     * Returns true if the node is using write through caching.
     * If enabled, block bodies and receipts are cached when they are written to persistent storage.
     * It is false by default
     *
     * @return
     */
    @Override
    public RpcResponse writeThroughCaching() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_WRITETHROUGHCACHING));
    }


    /**
     * Creates a rawTransaction based on the give transaction information
     *
     * @param fromAddress
     * @param toAddress
     * @param gas
     * @param gasPrice
     * @param value
     * @param data
     * @param nonce
     * @return
     */
    @Override
    public RpcResponse signTransaction(String fromAddress,String toAddress,String gas,String gasPrice,String value,String data,String nonce) {
        if (StringUtil.isEmpty(fromAddress) || StringUtil.isEmpty(gas) || StringUtil.isEmpty(gasPrice) || StringUtil.isEmpty(nonce)) {
            return new RpcResponse();
        }

        Object[] params = {RpcUtils.setKlayCall(fromAddress,toAddress,gas,gasPrice,value,data,nonce)};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_SIGNTRANSACTION,params));
    }


    /**
     * Returns receipts included in a block identified by block hash
     *
     * @param blockHash
     * @return
     */
    @Override
    public RpcResponse getBlockReceipts(String blockHash) {
        if (StringUtil.isEmpty(blockHash)) {
            return new RpcResponse();
        }

        String[] params = {blockHash};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETBLOCKRECEIPTS,params));
    }


    /**
     * Returns Keccak-256 (not the standardized SHA3-256) of the given data.
     *
     * @param data
     * @return
     */
    @Override
    public RpcResponse sha3(String data) {
        if (StringUtil.isEmpty(data)) {
            return new RpcResponse();
        }

        String[] params = {data};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_SHA3,params));
    }

    /**
     * Returns a list of all validators at the specified block.
     * If the parameter is not set, returns a list of all validators at the latest block
     *
     * @param blockParameter
     * @return
     */
    @Override
    @Deprecated
    public RpcResponse getValidators(String blockParameter) {
        Object[] params = {blockParameter};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.KLAY_GETVALIDATORS,params));
    }
}

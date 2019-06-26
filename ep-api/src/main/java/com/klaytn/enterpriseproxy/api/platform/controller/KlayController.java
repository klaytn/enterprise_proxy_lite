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
package com.klaytn.enterpriseproxy.api.platform.controller;

import com.klaytn.enterpriseproxy.api.common.exception.ApiException;
import com.klaytn.enterpriseproxy.api.common.model.ApiResponse;
import com.klaytn.enterpriseproxy.api.platform.service.KlayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;


@RestController
@Api("Platform Klay RESTFUL API")
@RequestMapping(value="/platform/klay/",method=RequestMethod.POST)
public class KlayController {
    @Autowired
    private KlayService service;




    @ApiOperation("Klay Protocol Version")
    @RequestMapping(value="protocolVersion")
    public ApiResponse protocolVersion(HttpServletRequest request) throws ApiException {
        return service.protocolVersion(request);
    }


    @ApiOperation("Klay Syncing")
    @RequestMapping(value="syncing")
    public ApiResponse syncing(HttpServletRequest request) throws ApiException {
        return service.syncing(request);
    }


    @ApiOperation("Klay Mining")
    @RequestMapping(value="mining")
    public ApiResponse mining(HttpServletRequest request) throws ApiException {
        return service.mining(request);
    }


    @ApiOperation("Klay Gas Price")
    @RequestMapping(value="gasPrice")
    public ApiResponse gasPrice(HttpServletRequest request) throws ApiException {
        return service.gasPrice(request);
    }

    @ApiOperation("Klay Gas Price At")
    @RequestMapping(value="gasPriceAt")
    public ApiResponse gasPriceAt(HttpServletRequest request,
              @RequestParam(value="blockParameter",required=false,defaultValue="") String blockParameter) throws ApiException {
        return service.gasPriceAt(request,blockParameter);
    }
    

    @ApiOperation("Klay Accounts")
    @RequestMapping(value="accounts")
    public ApiResponse accounts(HttpServletRequest request) throws ApiException {
        return service.accounts(request);
    }


    @ApiOperation("Klay Get Account")
    @RequestMapping(value="getAccount")
    public ApiResponse getAccount(
            HttpServletRequest request,
            @RequestParam(value="accountAddress",required=false,defaultValue="") String accountAddress,
            @RequestParam(value="blockParameter",required=false,defaultValue="") String blockParameter) throws ApiException {
        return service.getAccount(request, accountAddress, blockParameter);
    }


    @ApiOperation("Klay Get Account Key")
    @RequestMapping(value="getAccountKey")
    public ApiResponse getAccountKey(
            HttpServletRequest request,
            @RequestParam(value="accountAddress",required=false,defaultValue="") String accountAddress,
            @RequestParam(value="blockParameter",required=false,defaultValue="") String blockParameter) throws ApiException {
        return service.getAccountKey(request, accountAddress, blockParameter);
    }


    @ApiOperation("Klay Account Created")
    @RequestMapping(value="accountCreated")
    public ApiResponse accountCreated(
            HttpServletRequest request,
            @RequestParam(value="accountAddress",required=false,defaultValue="") String accountAddress,
            @RequestParam(value="blockParameter",required=false,defaultValue="") String blockParameter) throws ApiException {
        return service.accountCreated(request,accountAddress,blockParameter);
    }


    @ApiOperation("Klay Is Contract Account")
    @RequestMapping(value="isContractAccount")
    public ApiResponse isContractAccount(
            HttpServletRequest request,
            @RequestParam(value="accountAddress",required=false,defaultValue="") String accountAddress,
            @RequestParam(value="blockParameter",required=false,defaultValue="") String blockParameter) throws ApiException {
        return service.isContractAccount(request,accountAddress,blockParameter);
    }


    @ApiOperation("Klay Block Number")
    @RequestMapping(value="blockNumber")
    public ApiResponse blockNumber(HttpServletRequest request) throws ApiException {
        return service.blockNumber(request);
    }


    @ApiOperation("Klay Get Balance")
    @RequestMapping(value="getBalance")
    public ApiResponse getBalance(
            HttpServletRequest request,
            @RequestParam(value="checkAddress",required=false,defaultValue="") String checkAddress,
            @RequestParam(value="blockParameter",required=false,defaultValue="") String blockParameter) throws ApiException {
        return service.getBalance(request,checkAddress,blockParameter);
    }


    @ApiOperation("Klay Get Storage At")
    @RequestMapping(value="getStorageAt")
    public ApiResponse getStorageAt(
            HttpServletRequest request,
            @RequestParam(value="contractAddress",required=false,defaultValue="") String contractAddress,
            @RequestParam(value="position",required=false,defaultValue="") String position,
            @RequestParam(value="blockParameter",required=false,defaultValue="") String blockParameter) throws ApiException {
        return service.getStorageAt(request,contractAddress,position,blockParameter);
    }


    @ApiOperation("Klay Get Transaction Count")
    @RequestMapping(value="getTransactionCount")
    public ApiResponse getTransactionCount(
            HttpServletRequest request,
            @RequestParam(value="address",required=false,defaultValue="") String address,
            @RequestParam(value="blockParameter",required=false,defaultValue="") String blockParameter) throws ApiException {
        return service.getTransactionCount(request,address,blockParameter);
    }


    @ApiOperation("Klay Get Block Transaction Count By Hash")
    @RequestMapping(value="getBlockTransactionCountByHash")
    public ApiResponse getBlockTransactionCountByHash(
            HttpServletRequest request,
            @RequestParam(value="blockHash",required=false,defaultValue="") String blockHash) throws ApiException {
        return service.getBlockTransactionCountByHash(request,blockHash);
    }


    @ApiOperation("Klay Get Block Transaction Count By Number")
    @RequestMapping(value="getBlockTransactionCountByNumber")
    public ApiResponse getBlockTransactionCountByNumber(
            HttpServletRequest request,
            @RequestParam(value="blockParameter",required=false,defaultValue="") String blockParameter) throws ApiException {
        return service.getBlockTransactionCountByNumber(request,blockParameter);
    }


    @ApiOperation("Klay Get Code")
    @RequestMapping(value="getCode")
    public ApiResponse getCode(
            HttpServletRequest request,
            @RequestParam(value="contractAddress",required=false,defaultValue="") String contractAddress,
            @RequestParam(value="blockParameter",required=false,defaultValue="") String blockParameter) throws ApiException {
        return service.getCode(request,contractAddress,blockParameter);
    }


    @ApiOperation("Klay Sign")
    @RequestMapping(value="sign")
    public ApiResponse sign(
            HttpServletRequest request,
            @RequestParam(value="signAddress",required=false,defaultValue="") String signAddress,
            @RequestParam(value="message",required=false,defaultValue="") String message) throws ApiException {
        return service.sign(request,signAddress,message);
    }


    @ApiOperation("Klay Send Transaction")
    @RequestMapping(value="sendTransaction")
    public ApiResponse sendTransaction(
            HttpServletRequest request,
            @RequestParam(value="fromAddress",required=false,defaultValue="") String fromAddress,
            @RequestParam(value="toAddress",required=false,defaultValue="") String toAddress,
            @RequestParam(value="gas",required=false,defaultValue="") String gas,
            @RequestParam(value="gasPrice",required=false,defaultValue="") String gasPrice,
            @RequestParam(value="value",required=false,defaultValue="") String value,
            @RequestParam(value="data",required=false,defaultValue="") String data,
            @RequestParam(value="nonce",required=false,defaultValue="") String nonce) throws ApiException {
        return service.sendTransaction(request,fromAddress,toAddress,gas,gasPrice,value,data,nonce);
    }


    @ApiOperation("Klay Send Raw Transaction")
    @RequestMapping(value="sendRawTransaction")
    public ApiResponse sendRawTransaction(
            HttpServletRequest request,
            @RequestParam(value="transactionHash",required=false,defaultValue="") String transactionHash) throws ApiException {
        return service.sendRawTransaction(request,transactionHash);
    }


    @ApiOperation("Klay Call")
    @RequestMapping(value="call")
    public ApiResponse call(
            HttpServletRequest request,
            @RequestParam(value="fromAddress",required=false,defaultValue="") String fromAddress,
            @RequestParam(value="toAddress",required=false,defaultValue="") String toAddress,
            @RequestParam(value="gas",required=false,defaultValue="") String gas,
            @RequestParam(value="gasPrice",required=false,defaultValue="") String gasPrice,
            @RequestParam(value="value",required=false,defaultValue="") String value,
            @RequestParam(value="data",required=false,defaultValue="") String data,
            @RequestParam(value="blockParameter",required=false,defaultValue="") String blockParameter) throws ApiException {
        return service.call(request,fromAddress,toAddress,gas,gasPrice,value,data,blockParameter);
    }


    @ApiOperation("Klay Estimate Gas")
    @RequestMapping(value="estimateGas")
    public ApiResponse estimateGas(
            HttpServletRequest request,
            @RequestParam(value="fromAddress",required=false,defaultValue="") String fromAddress,
            @RequestParam(value="toAddress",required=false,defaultValue="") String toAddress,
            @RequestParam(value="gas",required=false,defaultValue="") String gas,
            @RequestParam(value="gasPrice",required=false,defaultValue="") String gasPrice,
            @RequestParam(value="value",required=false,defaultValue="") String value,
            @RequestParam(value="data",required=false,defaultValue="") String data,
            @RequestParam(value="blockParameter",required=false,defaultValue="") String blockParameter) throws ApiException {
        return service.estimateGas(request,fromAddress,toAddress,gas,gasPrice,value,data,blockParameter);
    }


    @ApiOperation("Klay Get Block By Hash")
    @RequestMapping(value="getBlockByHash")
    public ApiResponse getBlockByHash(
            HttpServletRequest request,
            @RequestParam(value="blockHash",required=false,defaultValue="") String blockHash,
            @RequestParam(value="returnTx",required=false,defaultValue="") boolean returnTx) throws ApiException {
        return service.getBlockByHash(request,blockHash,returnTx);
    }


    @ApiOperation("Klay Get Block By Number")
    @RequestMapping(value="getBlockByNumber")
    public ApiResponse getBlockByNumber(
            HttpServletRequest request,
            @RequestParam(value="blockParameter",required=false,defaultValue="") String blockParameter,
            @RequestParam(value="returnTx",required=false,defaultValue="") boolean returnTx) throws ApiException {
        return service.getBlockByNumber(request,blockParameter,returnTx);
    }


    @ApiOperation("Klay Get Transaction By Hash")
    @RequestMapping(value="getTransactionByHash")
    public ApiResponse getTransactionByHash(
            HttpServletRequest request,
            @RequestParam(value="transactionHash",required=false,defaultValue="") String transactionHash) throws ApiException {
        return service.getTransactionByHash(request,transactionHash);
    }

    @ApiOperation("Klay Get Transaction By Sender Tx Hash")
    @RequestMapping(value="getTransactionBySenderTxHash")
    public ApiResponse getTransactionBySenderTxHash(
            HttpServletRequest request,
            @RequestParam(value="transactionHash",required=false,defaultValue="") String transactionHash) throws ApiException {
        return service.getTransactionBySenderTxHash(request,transactionHash);
    }


    @ApiOperation("Klay Get Transaction By Block Hash And Index")
    @RequestMapping(value="getTransactionByBlockHashAndIndex")
    public ApiResponse getTransactionByBlockHashAndIndex(
            HttpServletRequest request,
            @RequestParam(value="blockHash",required=false,defaultValue="") String blockHash,
            @RequestParam(value="position",required=false,defaultValue="0x0") String position) throws ApiException {
        return service.getTransactionByBlockHashAndIndex(request,blockHash,position);
    }


    @ApiOperation("Klay Get Transaction by Block Number And Index")
    @RequestMapping(value="getTransactionByBlockNumberAndIndex")
    public ApiResponse getTransactionByBlockNumberAndIndex(
            HttpServletRequest request,
            @RequestParam(value="blockParameter",required=false,defaultValue="") String blockParameter,
            @RequestParam(value="position",required=false,defaultValue="0x0") String position) throws ApiException {
        return service.getTransactionByBlockNumberAndIndex(request,blockParameter,position);
    }


    @ApiOperation("Klay Get Transaction Receipt")
    @RequestMapping(value="getTransactionReceipt")
    public ApiResponse getTransactionReceipt(
            HttpServletRequest request,
            @RequestParam(value="transactionHash",required=false,defaultValue="") String transactionHash) throws ApiException {
        return service.getTransactionReceipt(request,transactionHash);
    }


    @ApiOperation("Klay Get Transaction Receipt By Sender Tx Hash")
    @RequestMapping(value="getTransactionReceiptBySenderTxHash")
    public ApiResponse getTransactionReceiptBySenderTxHash(
            HttpServletRequest request,
            @RequestParam(value="getTransactionReceiptBySenderTxHash",required=false,defaultValue="") String transactionHash) throws ApiException {
        return service.getTransactionReceiptBySenderTxHash(request,transactionHash);
    }

    @ApiOperation("Klay NewFilter")
    @RequestMapping(value="newFilter")
    public ApiResponse newFilter(
            HttpServletRequest request,
            @RequestParam(value="fromBlock",required=false,defaultValue="") String fromBlock,
            @RequestParam(value="toBlock",required=false,defaultValue="") String toBlock,
            @RequestParam(value="addresses",required=false,defaultValue="") String[] addresses,
            @RequestParam(value="topics",required=false,defaultValue="") String[] topics) throws ApiException {
        return service.newFilter(request,fromBlock,toBlock,addresses,topics);
    }


    @ApiOperation("Klay New Block Filter")
    @RequestMapping(value="newBlockFilter")
    public ApiResponse newBlockFilter(HttpServletRequest request) throws ApiException {
        return service.newBlockFilter(request);
    }


    @ApiOperation("Klay New Pending Transaction Filter")
    @RequestMapping(value="newPendingTransactionFilter")
    public ApiResponse newPendingTransactionFilter(HttpServletRequest request) throws ApiException {
        return service.newPendingTransactionFilter(request);
    }


    @ApiOperation("Klay Uninstall Filter")
    @RequestMapping(value="uninstallFilter")
    public ApiResponse uninstallFilter(
            HttpServletRequest request,
            @RequestParam(value="filterId",required=false,defaultValue="") String filterId) throws ApiException {
        return service.uninstallFilter(request,filterId);
    }


    @ApiOperation("Klay Get Filter Changes")
    @RequestMapping(value="getFilterChanges")
    public ApiResponse getFilterChanges(
            HttpServletRequest request,
            @RequestParam(value="filterId",required=false,defaultValue="") String filterId) throws ApiException {
        return service.getFilterChanges(request,filterId);
    }


    @ApiOperation("Klay Get Filter Logs")
    @RequestMapping(value="getFilterLogs")
    public ApiResponse getFilterLogs(
            HttpServletRequest request,
            @RequestParam(value="filterId",required=false,defaultValue="") String filterId) throws ApiException {
        return service.getFilterLogs(request,filterId);
    }


    @ApiOperation("Klay Get Logs")
    @RequestMapping(value="getLogs")
    public ApiResponse getLogs(
            HttpServletRequest request,
            @RequestParam(value="fromBlock",required=false,defaultValue="") String fromBlock,
            @RequestParam(value="toBlock",required=false,defaultValue="") String toBlock,
            @RequestParam(value="addresses",required=false,defaultValue="") String[] addresses,
            @RequestParam(value="topics",required=false,defaultValue="") String[] topics,
            @RequestParam(value="blockHash",required=false,defaultValue="") String blockHash) throws ApiException {
        return service.getLogs(request,fromBlock,toBlock,addresses,topics,blockHash);
    }


    @ApiOperation("Klay Get Work")
    @RequestMapping(value="getWork")
    public ApiResponse getWork(HttpServletRequest request) throws ApiException {
        return service.getWork(request);
    }


    @ApiOperation("Klay Get Block With Consensus Info By Hash")
    @RequestMapping(value="getBlockWithConsensusInfoByHash")
    public ApiResponse getBlockWithConsensusInfoByHash(
            HttpServletRequest request,
            @RequestParam(value="blockHash",required=false,defaultValue="") String blockHash) throws ApiException {
        return service.getBlockWithConsensusInfoByHash(request,blockHash);
    }


    @ApiOperation("Klay Get Block With Consensus Info By Number")
    @RequestMapping(value="getBlockWithConsensusInfoByNumber")
    public ApiResponse getBlockWithConsensusInfoByNumber(
            HttpServletRequest request,
            @RequestParam(value="blockParameter",required=false,defaultValue="") String blockParameter) throws ApiException {
        return service.getBlockWithConsensusInfoByNumber(request,blockParameter);
    }


    @ApiOperation("Klay Get Committee")
    @RequestMapping(value="getCommittee")
    public ApiResponse getCommittee(
            HttpServletRequest request,
            @RequestParam(value="blockParameter",required=false,defaultValue="") String blockParameter) throws ApiException {
        return service.getCommittee(request,blockParameter);
    }


    @ApiOperation("Klay Get Committee Size")
    @RequestMapping(value="getCommitteeSize")
    public ApiResponse getCommitteeSize(
            HttpServletRequest request,
            @RequestParam(value="blockParameter",required=false,defaultValue="") String blockParameter) throws ApiException {
        return service.getCommitteeSize(request,blockParameter);
    }


    @ApiOperation("Klay Get Council")
    @RequestMapping(value="getCouncil")
    public ApiResponse getCouncil(
            HttpServletRequest request,
            @RequestParam(value="blockParameter",required=false,defaultValue="") String blockParameter) throws ApiException {
        return service.getCouncil(request,blockParameter);
    }


    @ApiOperation("Klay Get Council Size")
    @RequestMapping(value="getCouncilSize")
    public ApiResponse getCouncilSize(
            HttpServletRequest request,
            @RequestParam(value="blockParameter",required=false,defaultValue="") String blockParameter) throws ApiException {
        return service.getCouncilSize(request,blockParameter);
    }

    @ApiOperation("Klay ChainID")
    @RequestMapping(value="chainID")
    public ApiResponse chainID(HttpServletRequest request) throws ApiException {
        return service.chainID(request);
    }

    @RequestMapping(value="clientVersion")
    public ApiResponse clientVersion(HttpServletRequest request) throws ApiException {
        return service.clientVersion(request);
    }


    @ApiOperation("Klay RewardBase")
    @RequestMapping(value="rewardbase")
    public ApiResponse rewardbase(HttpServletRequest request) throws ApiException {
        return service.rewardbase(request);
    }


    @ApiOperation("Klay is parallel db write")
    @RequestMapping(value="isParallelDBWrite")
    public ApiResponse isParallelDBWrite(HttpServletRequest request) throws ApiException {
        return service.isParallelDBWrite(request);
    }

    @ApiOperation("Klay is sender tx hash indexing enabled")
    @RequestMapping(value="isSenderTxHashIndexingEnabled")
    public ApiResponse isSenderTxHashIndexingEnabled(HttpServletRequest request) throws ApiException {
        return service.isSenderTxHashIndexingEnabled(request);
    }


    @ApiOperation("Klay write through caching")
    @RequestMapping(value="writeThroughCaching")
    public ApiResponse writeThroughCaching(HttpServletRequest request) throws ApiException {
        return service.writeThroughCaching(request);
    }


    @ApiOperation("Klay sign transaction")
    @RequestMapping(value="signTransaction")
    public ApiResponse signTransaction(
            HttpServletRequest request,
            @RequestParam(value="fromAddress",required=false,defaultValue="") String fromAddress,
            @RequestParam(value="toAddress",required=false,defaultValue="") String toAddress,
            @RequestParam(value="gas",required=false,defaultValue="") String gas,
            @RequestParam(value="gasPrice",required=false,defaultValue="") String gasPrice,
            @RequestParam(value="value",required=false,defaultValue="") String value,
            @RequestParam(value="data",required=false,defaultValue="") String data,
            @RequestParam(value="nonce",required=false,defaultValue="") String nonce) throws ApiException {
        return service.signTransaction(request,fromAddress,toAddress,gas,gasPrice,value,data,nonce);
    }


    @ApiOperation("Klay Get Block Receipts")
    @RequestMapping(value="getBlockReceipts")
    public ApiResponse getBlockReceipts(
            HttpServletRequest request,
            @RequestParam(value="blockHash",required=false,defaultValue="") String blockHash) throws ApiException {
        return service.getBlockReceipts(request,blockHash);
    }

    @ApiOperation("Klay Sha3")
    @RequestMapping(value="sha3")
    public ApiResponse sha3(
            HttpServletRequest request,
            @RequestParam(value="data",required=false,defaultValue="") String data) throws ApiException {
        return service.sha3(request,data);
    }
}

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
package com.klaytn.enterpriseproxy.api.management.service;

import com.klaytn.enterpriseproxy.api.common.model.ApiResponse;
import com.klaytn.enterpriseproxy.api.common.util.ApiUtils;
import com.klaytn.enterpriseproxy.rpc.common.config.RpcProperties;
import com.klaytn.enterpriseproxy.rpc.management.debug.Debug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;


@Service
public class DebugService {
    @Autowired
    private RpcProperties rpc;


    
    /**
     * back tract At
     *
     * @param httpServletRequest
     * @param location
     * @return
     */
    public ApiResponse backtraceAt(HttpServletRequest httpServletRequest,String location) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).backtraceAt(location));
    }


    /**
     * block profile
     *
     * @param httpServletRequest
     * @param file
     * @param seconds
     * @return
     */
    public ApiResponse blockProfile(HttpServletRequest httpServletRequest,String file,int seconds) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).blockProfile(file,seconds));
    }


    /**
     * cpu profile
     *
     * @param httpServletRequest
     * @param file
     * @param seconds
     * @return
     */
    public ApiResponse cpuProfile(HttpServletRequest httpServletRequest,String file,int seconds) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).cpuProfile(file,seconds));
    }


    /**
     * dump block
     *
     * @param httpServletRequest
     * @param blockNumber
     * @return
     */
    public ApiResponse dumpBlock(HttpServletRequest httpServletRequest,String blockNumber) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).dumpBlock(blockNumber));
    }


    /**
     * gc stats
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse gcStats(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).gcStats());
    }


    /**
     * get block rlp
     *
     * @param httpServletRequest
     * @param blockNumber
     * @return
     */
    public ApiResponse getBlockRlp(HttpServletRequest httpServletRequest,BigInteger blockNumber) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).getBlockRlp(blockNumber));
    }


    /**
     * go trace
     *
     * @param httpServletRequest
     * @param file
     * @param seconds
     * @return
     */
    public ApiResponse goTrace(HttpServletRequest httpServletRequest,String file,int seconds) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).goTrace(file,seconds));
    }


    /**
     * mem stats
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse memStats(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).memStats());
    }


    /**
     * set head
     *
     * @param httpServletRequest
     * @param blockNumber
     * @return
     */
    public ApiResponse setHead(HttpServletRequest httpServletRequest,String blockNumber) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).setHead(blockNumber));
    }


    /**
     * set VMLog Target
     *
     * @param httpServletRequest
     * @param target
     * @return
     */
    public ApiResponse setVMLogTarget(HttpServletRequest httpServletRequest,int target) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).setVMLogTarget(target));
    }


    /**
     * set block profile rate
     *
     * @param httpServletRequest
     * @param rate
     * @return
     */
    public ApiResponse setBlockProfileRate(HttpServletRequest httpServletRequest,int rate) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).setBlockProfileRate(rate));
    }


    /**
     * get modified accounts by hash
     *
     * @param httpServletRequest
     * @param startBlockHash
     * @param endBlockHash
     * @return
     */
    public ApiResponse getModifiedAccountsByHash(HttpServletRequest httpServletRequest,String startBlockHash,String endBlockHash) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).getModifiedAccountsByHash(startBlockHash,endBlockHash));
    }



    /**
     * get modified accounts by number
     *
     * @param httpServletRequest
     * @param startBlockNumber
     * @param endBlockNumber
     * @return
     */
    public ApiResponse getModifiedAccountsByNumber(HttpServletRequest httpServletRequest,BigInteger startBlockNumber,BigInteger endBlockNumber) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).getModifiedAccountsByNumber(startBlockNumber,endBlockNumber));
    }


    /**
     * stacks
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse stacks(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).stacks());
    }


    /**
     * start cpu profile
     *
     * @param httpServletRequest
     * @param file
     * @return
     */
    public ApiResponse startCPUProfile(HttpServletRequest httpServletRequest,String file) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).startCPUProfile(file));
    }


    /**
     * start go trace
     *
     * @param httpServletRequest
     * @param file
     * @return
     */
    public ApiResponse startGoTrace(HttpServletRequest httpServletRequest,String file) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).startGoTrace(file));
    }


    /**
     * stop cpu profile
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse stopCPUProfile(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).stopCPUProfile());
    }


    /**
     * stop go trace
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse stopGoTrace(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).stopGoTrace());
    }


    /**
     * verbosity
     *
     * @param httpServletRequest
     * @param level
     * @return
     */
    public ApiResponse verbosity(HttpServletRequest httpServletRequest,int level) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).verbosity(level));
    }


    /**
     * vmodule
     *
     * @param httpServletRequest
     * @param module
     * @return
     */
    public ApiResponse vmodule(HttpServletRequest httpServletRequest,String module) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).vmodule(module));
    }


    /**
     * write block profile
     *
     * @param httpServletRequest
     * @param file
     * @return
     */
    public ApiResponse writeBlockProfile(HttpServletRequest httpServletRequest,String file) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).writeBlockProfile(file));
    }


    /**
     * write mem profile
     *
     * @param httpServletRequest
     * @param file
     * @return
     */
    public ApiResponse writeMemProfile(HttpServletRequest httpServletRequest,String file) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).writeMemProfile(file));
    }


    /**
     * starts the pprof HTTP Server
     *
     * @param httpServletRequest
     * @param host
     * @param port
     * @return
     */
    public ApiResponse startPProf(HttpServletRequest httpServletRequest,String host,int port) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).startPProf(host,port));
    }


    /**
     * stops the pprof HTTP Server
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse stopPProf(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).stopPProf());
    }


    /**
     * pprof HTTP server is running
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse isPProfRunning(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).isPProfRunning());
    }


    /**
     * debug metrics
     *
     * @param httpServletRequest
     * @param isRaw
     * @return
     */
    public ApiResponse metrics(HttpServletRequest httpServletRequest,boolean isRaw) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).metrics(isRaw));
    }


    /**
     * print block
     *
     * @param httpServletRequest
     * @param blockNumber
     * @return
     */
    public ApiResponse printBlock(HttpServletRequest httpServletRequest,BigInteger blockNumber) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).printBlock(blockNumber));
    }


    /**
     * print block
     *
     * @param httpServletRequest
     * @param sha3hash
     * @return
     */
    public ApiResponse preImage(HttpServletRequest httpServletRequest,String sha3hash) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).preImage(sha3hash));
    }


    /**
     * returns unused memory to the OS
     *
     * @param httpServletRequest
     * @return
     */
    public ApiResponse freeOSMemory(HttpServletRequest httpServletRequest) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).freeOSMemory());
    }


    /**
     * set gc percent
     *
     * @param httpServletRequest
     * @param percent
     * @return
     */
    public ApiResponse setGCPercent(HttpServletRequest httpServletRequest,int percent) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).setGCPercent(percent));
    }


    /**
     * trace block
     *
     * @param httpServletRequest
     * @param blockRlp
     * @param tracerName
     * @return
     */
    public ApiResponse traceBlock(HttpServletRequest httpServletRequest,String blockRlp,String tracerName) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).traceBlock(blockRlp,tracerName));
    }


    /**
     * trace block by number
     *
     * @param httpServletRequest
     * @param blockNumber
     * @param tracerName
     * @return
     */
    public ApiResponse traceBlockByNumber(HttpServletRequest httpServletRequest,String blockNumber,String tracerName) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).traceBlockByNumber(blockNumber,tracerName));
    }


    /**
     * trace block by hash
     *
     * @param httpServletRequest
     * @param blockHash
     * @param tracerName
     * @return
     */
    public ApiResponse traceBlockByHash(HttpServletRequest httpServletRequest,String blockHash,String tracerName) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).traceBlockByHash(blockHash,tracerName));
    }


    /**
     * trace block from file
     *
     * @param httpServletRequest
     * @param filename
     * @param tracerName
     * @return
     */
    public ApiResponse traceBlockFromFile(HttpServletRequest httpServletRequest,String filename,String tracerName) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).traceBlockFromFile(filename,tracerName));
    }


    /**
     * trace transaction
     *
     * @param httpServletRequest
     * @param txHash
     * @param tracerName
     * @return
     */
    public ApiResponse traceTransaction(HttpServletRequest httpServletRequest,String txHash,String tracerName) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).traceTransaction(txHash,tracerName));
    }


    /**
     * trace bad block
     *
     * @param httpServletRequest
     * @param blockHash
     * @param tracerName
     * @return
     */
    public ApiResponse traceBadBlock(HttpServletRequest httpServletRequest,String blockHash,String tracerName) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).traceBadBlock(blockHash,tracerName));
    }


    /**
     * standard trace bad block to file
     *
     * @param httpServletRequest
     * @param blockHash
     * @param tracerName
     * @return
     */
    public ApiResponse standardTraceBadBlockToFile(HttpServletRequest httpServletRequest,String blockHash,String tracerName) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).standardTraceBadBlockToFile(blockHash,tracerName));
    }


    /**
     * standard trace block to file
     *
     * @param httpServletRequest
     * @param blockHash
     * @param tracerName
     * @return
     */
    public ApiResponse standardTraceBlockToFile(HttpServletRequest httpServletRequest,String blockHash,String tracerName) {
        String targetHost = ApiUtils.getTargetHost(httpServletRequest,rpc);
        return ApiUtils.onRpcResponse(Debug.build(targetHost).standardTraceBlockToFile(blockHash,tracerName));
    }
}

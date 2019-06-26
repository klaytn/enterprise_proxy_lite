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
package com.klaytn.enterpriseproxy.rpc.management.debug;

import com.klaytn.enterpriseproxy.rpc.common.model.RpcResponse;

import java.math.BigInteger;


public interface Debug {
    static Debug build(String targetHost) {
        return new DebugRpc(targetHost);
    }



    
    RpcResponse startPProf(String host,int port);

    RpcResponse stopPProf();

    RpcResponse isPProfRunning();

    RpcResponse getModifiedAccountsByHash(String startBlockHash,String endBlockHash);

    RpcResponse getModifiedAccountsByNumber(BigInteger startBlockNum,BigInteger endBlockNum);

    RpcResponse backtraceAt(String location);

    RpcResponse blockProfile(String fileName, int duration);

    RpcResponse cpuProfile(String fileName, int duration);

    RpcResponse dumpBlock(String blockHex);

    RpcResponse gcStats();

    RpcResponse getBlockRlp(BigInteger blockNumber);

    RpcResponse goTrace(String fileName, int duration);

    RpcResponse memStats();

    RpcResponse metrics(boolean isRaw);

    RpcResponse printBlock(BigInteger blockNumber);

    RpcResponse preImage(String sha3hash);

    RpcResponse freeOSMemory();

    RpcResponse setHead(String blockHex);

    RpcResponse setBlockProfileRate(int rate);

    RpcResponse setVMLogTarget(int target);

    RpcResponse stacks();

    RpcResponse startCPUProfile(String fileName);

    RpcResponse stopCPUProfile();

    RpcResponse startGoTrace(String fileName);

    RpcResponse stopGoTrace();

    RpcResponse verbosity(int level);

    RpcResponse vmodule(String module);

    RpcResponse writeBlockProfile(String fileName);

    RpcResponse writeMemProfile(String fileName);

    RpcResponse setGCPercent(int percent);

    RpcResponse traceBlock(String blockRlp,String tracerName);

    RpcResponse traceBlockByNumber(String blockHex,String tracerName);

    RpcResponse traceBlockByHash(String blockHash,String tracerName);

    RpcResponse traceBlockFromFile(String fileName,String tracerName);

    RpcResponse traceTransaction(String transactionHash,String tracerName);

    RpcResponse traceBadBlock(String blockHash,String tracerName);

    RpcResponse standardTraceBadBlockToFile(String blockHash,String tracerName);

    RpcResponse standardTraceBlockToFile(String blockHash,String tracerName);
}

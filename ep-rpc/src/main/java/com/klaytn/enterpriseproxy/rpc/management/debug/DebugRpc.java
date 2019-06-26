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

import com.klaytn.enterpriseproxy.rpc.common.model.RpcMethods;
import com.klaytn.enterpriseproxy.rpc.common.model.RpcResponse;
import com.klaytn.enterpriseproxy.rpc.common.transfer.RpcTransfer;
import com.klaytn.enterpriseproxy.rpc.common.util.RpcUtils;
import com.klaytn.enterpriseproxy.utils.ObjectUtil;
import com.klaytn.enterpriseproxy.utils.StringUtil;

import java.math.BigInteger;


public class DebugRpc implements Debug {
    private RpcTransfer transfer;




    public DebugRpc(String targetHost) {
        this.transfer = new RpcTransfer(targetHost);
    }


    /**
     * Starts the pprof HTTP server.
     *
     * @return
     */
    @Override
    public RpcResponse startPProf(String host,int port) {
        host = (StringUtil.isEmpty(host)) ? "localhost" : host;
        port = (port <= 0) ? 6060 : port;

        Object[] params = {host, port};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_STARTPPROF,params));
    }


    /**
     * stops the pprof HTTP Server
     *
     * @return
     */
    @Override
    public RpcResponse stopPProf() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_STOPPPROF));
    }


    /**
     * pprof HTTP server is running
     *
     * @return
     */
    @Override
    public RpcResponse isPProfRunning() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_ISPPROFRUNNING));
    }


    /**
     * Returns all accounts that have changed between the two blocks specified by their block hashes.
     * A change is defined as a difference in nonce, balance, code hash, or storage hash
     *
     * @param startBlockHash
     * @param endBlockHash
     * @return
     */
    @Override
    public RpcResponse getModifiedAccountsByHash(String startBlockHash,String endBlockHash) {
        if (!StringUtil.isHexadecimal(startBlockHash) || !StringUtil.isHexadecimal(endBlockHash)) {
            return new RpcResponse();
        }

        String[] params = {startBlockHash,endBlockHash};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_GETMODIFIEDACCOUNTSBYHASH,params));
    }


    /**
     * Returns all accounts that have changed between the two blocks specified by their block numbers.
     * A change is defined as a difference in nonce, balance, code hash, or storage hash
     *
     * @param startBlockNumber
     * @param endBlockNumber
     * @return
     */
    @Override
    public RpcResponse getModifiedAccountsByNumber(BigInteger startBlockNumber,BigInteger endBlockNumber) {
        if (!RpcUtils.isValidBiginteger(startBlockNumber) || !RpcUtils.isValidBiginteger(endBlockNumber)) {
            return new RpcResponse();
        }

        Object[] params = {startBlockNumber,endBlockNumber};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_GETMODIFIEDACCOUNTSBYNUMBER,params));
    }


    /**
     * Sets the logging backtrace location.
     * When a backtrace location is set and a log message is emitted at that location,
     * the stack of the goroutine executing the log statement will be printed to stderr
     *
     * @param location
     * @return
     */
    @Override
    public RpcResponse backtraceAt(String location) {
        if (ObjectUtil.isEmpty(location)) {
            return new RpcResponse();
        }
        
        Object[] params = {location};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_BACKTRACEAT,params));
    }


    /**
     * Turns on block profiling for the given duration and writes profile data to disk.
     * It uses a profile rate of 1 for most accurate information
     *
     * @param fileName
     * @param duration
     * @return
     */
    @Override
    public RpcResponse blockProfile(String fileName,int duration) {
        if (ObjectUtil.isEmpty(fileName) || duration < 0) {
            return new RpcResponse();
        }

        Object[] params = {fileName,duration};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_BLOCKPROFILE,params));
    }


    /**
     * Turns on CPU profiling for the given duration and writes profile data to disk
     *
     * @param fileName
     * @param duration
     * @return
     */
    @Override
    public RpcResponse cpuProfile(String fileName, int duration) {
        if (ObjectUtil.isEmpty(fileName) || duration < 0) {
            return new RpcResponse();
        }

        Object[] params = {fileName,duration};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_CPUPROFILE,params));
    }


    /**
     * Retrieves the state that corresponds to the block number and returns a list of accounts
     *
     * @param blockHex
     * @return
     */
    @Override
    public RpcResponse dumpBlock(String blockHex) {
        if (!StringUtil.isHexadecimal(blockHex)) {
            return new RpcResponse();
        }

        Object[] params = {blockHex};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_DUMPBLOCK,params));
    }


    /**
     * Returns GC statistics
     *
     * @return
     */
    @Override
    public RpcResponse gcStats() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_GCSTATS));
    }


    /**
     * Retrieves and returns the RLP-encoded block by the block number
     *
     * @param blockNumber
     * @return
     */
    @Override
    public RpcResponse getBlockRlp(BigInteger blockNumber) {
        if (!RpcUtils.isValidBiginteger(blockNumber)) {
            return new RpcResponse();
        }
        
        Object[] params = {blockNumber};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_GETBLOCKRLP,params));
    }


    /**
     * Turns on Go runtime tracing for the given duration and writes trace data to disk
     *
     * @param fileName
     * @param duration
     * @return
     */
    @Override
    public RpcResponse goTrace(String fileName, int duration) {
        if (ObjectUtil.isEmpty(fileName) || duration < 0) {
            return new RpcResponse();
        }

        Object[] params = {fileName,duration};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_GOTRACE,params));
    }


    /**
     * Returns detailed runtime memory statistics
     *
     * @return
     */
    @Override
    public RpcResponse memStats() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_MEMSTATS));
    }


    /**
     * Retrieves all the known system metrics collected by the node
     *
     * @param isRaw
     * @return
     */
    @Override
    public RpcResponse metrics(boolean isRaw) {
        Object[] params = {isRaw};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_METRICS,params));
    }


    /**
     * Retrieves a block and returns its pretty printed form
     *
     * @param blockNumber
     * @return
     */
    @Override
    public RpcResponse printBlock(BigInteger blockNumber) {
        if (!RpcUtils.isValidBiginteger(blockNumber)) {
            return new RpcResponse();
        }

        Object[] params = {blockNumber};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_PRINTBLOCK,params));
    }


    /**
     * Returns the preimage for a sha3 hash, if known
     *
     * @param sha3hash
     * @return
     */
    @Override
    public RpcResponse preImage(String sha3hash) {
        if (StringUtil.isEmpty(sha3hash)) {
            return new RpcResponse();
        }

        String[] params = {sha3hash};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_PREIMAGE,params));
    }


    /**
     * returns unused memory to the OS
     *
     * @return
     */
    @Override
    public RpcResponse freeOSMemory() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_FREEOSMEMOEY));
    }


    /**
     * Sets the current head of the local chain by block number
     *
     * @param blockHex
     * @return
     */
    @Override
    public RpcResponse setHead(String blockHex) {
        if (!StringUtil.isHexadecimal(blockHex)) {
            return new RpcResponse();
        }

        Object[] params = {blockHex};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_SETHEAD,params));
    }


    /**
     * Sets the rate (in samples/sec) of goroutine block profile data collection
     *
     * @param rate
     * @return
     */
    @Override
    public RpcResponse setBlockProfileRate(int rate) {
        if (rate < 0) {
            return new RpcResponse();
        }
        
        Object[] params = {rate};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_SETBLOCKPROFILERATE,params));
    }


    /**
     * Sets the output target of vmlog precompiled contract
     *  - output target (0:no output,1:file,2:stdout,3:both)
     *
     * @param target
     * @return
     */
    @Override
    public RpcResponse setVMLogTarget(int target) {
        if (target < 0 || target > 3) {
            return new RpcResponse();
        }

        Object[] params = {target};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_SETVMLOGTARGET,params));
    }


    /**
     * Returns a printed representation of the stacks of all goroutines
     *
     * @return
     */
    @Override
    public RpcResponse stacks() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_STACKS));
    }


    /**
     * Turns on CPU profiling indefinitely, writing to the given file
     *
     * @param fileName
     * @return
     */
    @Override
    public RpcResponse startCPUProfile(String fileName) {
        if (ObjectUtil.isEmpty(fileName)) {
            return new RpcResponse();
        }

        Object[] params = {fileName};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_STARTCPUPROFILE,params));
    }


    /**
     * Turns off CPU profiling
     *
     * @return
     */
    @Override
    public RpcResponse stopCPUProfile() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_STOPCPUPROFILE));
    }


    /**
     * Starts writing a Go runtime trace to the given file
     *
     * @param fileName
     * @return
     */
    @Override
    public RpcResponse startGoTrace(String fileName) {
        if (ObjectUtil.isEmpty(fileName)) {
            return new RpcResponse();
        }
        
        Object[] params = {fileName};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_STARTGOTRACE,params));
    }


    /**
     * Stops writing the Go runtime trace
     *
     * @return
     */
    @Override
    public RpcResponse stopGoTrace() {
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_STOPGOTRACE));
    }


    /**
     * Sets the logging verbosity ceiling. Log messages with level up to and including the given level will be printed
     *
     * @param level
     * @return
     */
    @Override
    public RpcResponse verbosity(int level) {
        if (level < 0 || level > 5) {
            return new RpcResponse();
        }
        
        Object[] params = {level};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_VERBOSITY,params));
    }


    /**
     * Sets the logging verbosity pattern
     *
     * @param module
     * @return
     */
    @Override
    public RpcResponse vmodule(String module) {
        if (ObjectUtil.isEmpty(module)) {
            return new RpcResponse();
        }
        
        String[] params = {module};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_VMODULE,params));
    }


    /**
     * Writes a goroutine blocking profile to the given file
     *
     * @param fileName
     * @return
     */
    @Override
    public RpcResponse writeBlockProfile(String fileName) {
        if (ObjectUtil.isEmpty(fileName)) {
            return new RpcResponse();
        }
        
        String[] params = {fileName};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_WRITEBLOCKPROFILE,params));
    }


    /**
     * Writes an allocation profile to the given file
     *
     * @param fileName
     * @return
     */
    @Override
    public RpcResponse writeMemProfile(String fileName) {
        if (ObjectUtil.isEmpty(fileName)) {
            return new RpcResponse();
        }
        
        String[] params = {fileName};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_WRITEMEMPROFILE,params));
    }


    /**
     * Sets the garbage collection target percentage. It returns the previous setting. A negative value disables GC
     *
     * @param percent
     * @return
     */
    @Override
    public RpcResponse setGCPercent(int percent) {
        if (percent < 0) {
            return new RpcResponse();
        }

        Object[] params = {percent};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_SETGCPERCENT,params));
    }


    /**
     * The traceBlock method will return a full stack trace of all invoked opcodes of all transactions that were included in this block
     *
     * @param blockRlp
     * @param tracerName
     * @return
     */
    @Override
    public RpcResponse traceBlock(String blockRlp,String tracerName) {
        if (ObjectUtil.isEmpty(blockRlp)) {
            return new RpcResponse();
        }

        Object[] params = {blockRlp,RpcUtils.setTracerCall(tracerName)};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_TRACEBLOCK,params));
    }


    /**
     * Similar to debug_traceBlock, traceBlockByNumber accepts a block number and will replay the block that is already present in the database
     *
     * @param blockHex
     * @param tracerName
     * @return
     */
    @Override
    public RpcResponse traceBlockByNumber(String blockHex,String tracerName) {
        if (!StringUtil.isHexadecimal(blockHex)) {
            return new RpcResponse();
        }

        Object[] params = {blockHex,RpcUtils.setTracerCall(tracerName)};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_TRACEBLOCKBYNUMBER,params));
    }


    /**
     * Similar to debug_traceBlock, traceBlockByHash accepts a block hash and will replay the block that is already present in the database
     *
     * @param blockHash
     * @param tracerName
     * @return
     */
    @Override
    public RpcResponse traceBlockByHash(String blockHash,String tracerName) {
        if (!StringUtil.isHexadecimal(blockHash)) {
            return new RpcResponse();
        }

        Object[] params = {blockHash,RpcUtils.setTracerCall(tracerName)};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_TRACEBLOCKBYHASH,params));
    }


    /**
     * Similar to debug_traceBlock, traceBlockFromFile accepts a file containing the RLP of the block.
     *
     * @param fileName
     * @param tracerName
     * @return
     */
    @Override
    public RpcResponse traceBlockFromFile(String fileName,String tracerName) {
        if (ObjectUtil.isEmpty(fileName)) {
            return new RpcResponse();
        }

        Object[] params = {fileName,RpcUtils.setTracerCall(tracerName)};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_TRACEBLOCKFROMFILE,params));
    }


    /**
     * The traceTransaction debugging method will attempt to run the transaction in the exact same manner as it was executed on the network.
     *
     * @param transactionHash
     * @param tracerName
     * @return
     */
    @Override
    public RpcResponse traceTransaction(String transactionHash,String tracerName) {
        if (!StringUtil.isHexadecimal(transactionHash)) {
            return new RpcResponse();
        }

        Object[] params = {transactionHash,RpcUtils.setTracerCall(tracerName)};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_TRACETRANSACTION,params));
    }


    /**
     *  The traceBadBlock method will return a full stack trace of all invoked opcodes of all transactions that were included in this block.
     *
     * @param blockHash
     * @param tracerName
     * @return
     */
    @Override
    public RpcResponse traceBadBlock(String blockHash,String tracerName) {
        if (!StringUtil.isHexadecimal(blockHash)) {
            return new RpcResponse();
        }

        Object[] params = {blockHash,RpcUtils.setTracerCall(tracerName)};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_TRACEBADBLOCK,params));
    }


    /**
     * Similar to debug_traceBlock, standardTraceBlockToFile accepts a block hash and will replay the block
     * that is already present in the database. It returns a list of file names containing tracing result.
     *
     * Note that the files will be stored in the machine that serves this API.
     * 
     * @param blockHash
     * @param tracerName
     * @return
     */
    @Override
    public RpcResponse standardTraceBadBlockToFile(String blockHash,String tracerName) {
        if (!StringUtil.isHexadecimal(blockHash)) {
            return new RpcResponse();
        }

        Object[] params = {blockHash,RpcUtils.setTracerCall(tracerName)};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_STANDARDTRACEBADBLOCKTOFILE,params));
    }


    /**
     * You may give trace API function a secondary optional argument, which specifies the options for this specific call
     *
     * @param blockHash
     * @param tracerName
     * @return
     */
    @Override
    public RpcResponse standardTraceBlockToFile(String blockHash,String tracerName) {
        if (!StringUtil.isHexadecimal(blockHash)) {
            return new RpcResponse();
        }

        Object[] params = {blockHash,RpcUtils.setTracerCall(tracerName)};
        return transfer.call(RpcUtils.madeRequest(RpcMethods.DEBUG_STANDARDTRACEBLOCKTOFILE,params));
    }
}

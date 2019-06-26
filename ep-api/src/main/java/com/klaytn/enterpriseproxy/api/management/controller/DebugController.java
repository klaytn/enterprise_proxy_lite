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
package com.klaytn.enterpriseproxy.api.management.controller;

import com.klaytn.enterpriseproxy.api.common.exception.ApiException;
import com.klaytn.enterpriseproxy.api.common.model.ApiResponse;
import com.klaytn.enterpriseproxy.api.management.service.DebugService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;


@RestController
@Api("Management Debug RESTFUL API")
@RequestMapping(value="/management/debug/",method=RequestMethod.POST)
public class DebugController {
    @Autowired
    private DebugService service;



    
    @ApiOperation(value="Debug BackTraceAt")
    @RequestMapping(value="backtraceAt")
    public ApiResponse backtraceAt(
            HttpServletRequest request,
            @RequestParam(value="location",required=false,defaultValue="") String location) throws ApiException {
        return service.backtraceAt(request,location);
    }


    @ApiOperation(value="Debug Block Profile")
    @RequestMapping(value="blockProfile")
    public ApiResponse blockProfile(
            HttpServletRequest request,
            @RequestParam(value="fileName",required=false,defaultValue="") String fileName,
            @RequestParam(value="duration",required=false,defaultValue="0") int duration) throws ApiException {
        return service.blockProfile(request,fileName,duration);
    }


    @ApiOperation(value="Debug Get Modified Accounts By Hash")
    @RequestMapping(value="getModifiedAccountsByHash")
    public ApiResponse getModifiedAccountsByHash(
            HttpServletRequest request,
            @RequestParam(value="startBlockHash",required=false,defaultValue="") String startBlockHash,
            @RequestParam(value="endBlockHash",required=false,defaultValue="") String endBlockHash) throws ApiException {
        return service.getModifiedAccountsByHash(request,startBlockHash,endBlockHash);
    }


    @ApiOperation(value="Debug Get Modified Account By Number")
    @RequestMapping(value="getModifiedAccountsByNumber")
    public ApiResponse getModifiedAccountsByNumber(
            HttpServletRequest request,
            @RequestParam(value="startBlockNumber",required=false,defaultValue="0") BigInteger startBlockNumber,
            @RequestParam(value="endBlockNumber",required=false,defaultValue="0") BigInteger endBlockNumber) throws ApiException {
        return service.getModifiedAccountsByNumber(request,startBlockNumber,endBlockNumber);
    }


    @ApiOperation(value="Debug Cpu Profile")
    @RequestMapping(value="cpuProfile")
    public ApiResponse cpuProfile(
            HttpServletRequest request,
            @RequestParam(value="file",required=false,defaultValue="") String file,
            @RequestParam(value="duration",required=false,defaultValue="0") int duration) throws ApiException {
        return service.cpuProfile(request,file,duration);
    }


    @ApiOperation(value="Debug Dump Block")
    @RequestMapping(value="dumpBlock")
    public ApiResponse dumpBlock(
            HttpServletRequest request,
            @RequestParam(value="blockHex",required=false,defaultValue="") String blockHex) throws ApiException {
        return service.dumpBlock(request,blockHex);
    }


    @ApiOperation(value="Debug GC Stats")
    @RequestMapping(value="gcStats")
    public ApiResponse gcStats(HttpServletRequest request) throws ApiException {
        return service.gcStats(request);
    }


    @ApiOperation(value="Debug Get Block Rlp")
    @RequestMapping(value="getBlockRlp")
    public ApiResponse getBlockRlp(
            HttpServletRequest request,
            @RequestParam(value="blockNumber",required=false,defaultValue="0") BigInteger blockNumber) throws ApiException {
        return service.getBlockRlp(request,blockNumber);
    }


    @ApiOperation(value="Debug Go Trace")
    @RequestMapping(value="goTrace")
    public ApiResponse goTrace(
            HttpServletRequest request,
            @RequestParam(value="fileName",required=false,defaultValue="") String fileName,
            @RequestParam(value="duration",required=false,defaultValue="0") int duration) throws ApiException {
        return service.goTrace(request,fileName,duration);
    }


    @ApiOperation(value="Debug Mem Stats")
    @RequestMapping(value="memStats")
    public ApiResponse memStats(HttpServletRequest request) throws ApiException {
        return service.memStats(request);
    }


    @ApiOperation(value="Debug Set Head")
    @RequestMapping(value="setHead")
    public ApiResponse setHead(
            HttpServletRequest request,
            @RequestParam(value="blockHex",required=false,defaultValue="") String blockHex) throws ApiException {
        return service.setHead(request,blockHex);
    }


    @ApiOperation(value="Debug Set VMLog Target")
    @RequestMapping(value="setVMLogTarget")
    public ApiResponse setVMLogTarget(
            HttpServletRequest request,
            @RequestParam(value="target",required=false,defaultValue="0") int target) throws ApiException {
        return service.setVMLogTarget(request,target);
    }


    @ApiOperation(value="Debug Set Block Profile Rate")
    @RequestMapping(value="setBlockProfileRate")
    public ApiResponse setBlockProfileRate(
            HttpServletRequest request,
            @RequestParam(value="rate",required=false,defaultValue="0") int rate) throws ApiException {
        return service.setBlockProfileRate(request,rate);
    }


    @ApiOperation(value="Debug Stacks")
    @RequestMapping(value="stacks")
    public ApiResponse stacks(HttpServletRequest request) throws ApiException {
        return service.stacks(request);
    }


    @ApiOperation(value="Debug Start Cpu Profile")
    @RequestMapping(value="startCPUProfile")
    public ApiResponse startCPUProfile(
            HttpServletRequest request,
            @RequestParam(value="fileName",required=false,defaultValue="") String fileName) throws ApiException {
        return service.startCPUProfile(request,fileName);
    }


    @ApiOperation(value="Debug Start Go Trace")
    @RequestMapping(value="startGoTrace")
    public ApiResponse startGoTrace(
            HttpServletRequest request,
            @RequestParam(value="fileName",required=false,defaultValue="") String fileName) throws ApiException {
        return service.startGoTrace(request,fileName);
    }


    @ApiOperation(value="Debug Stop Cpu Profile")
    @RequestMapping(value="stopCPUProfile")
    public ApiResponse stopCPUProfile(HttpServletRequest request) throws ApiException {
        return service.stopCPUProfile(request);
    }


    @ApiOperation(value="Debug Stop Go Trace")
    @RequestMapping(value="stopGoTrace")
    public ApiResponse stopGoTrace(HttpServletRequest request) throws ApiException {
        return service.stopGoTrace(request);
    }


    @ApiOperation(value="Debug Verbosity")
    @RequestMapping(value="verbosity")
    public ApiResponse verbosity(
            HttpServletRequest request,
            @RequestParam(value="level",required=false,defaultValue="0") int level) throws ApiException {
        return service.verbosity(request,level);
    }


    @ApiOperation(value="Debug Vmodule")
    @RequestMapping(value="vmodule")
    public ApiResponse vmodule(
            HttpServletRequest request,
            @RequestParam(value="module",required=false,defaultValue="") String module) throws ApiException {
        return service.vmodule(request,module);
    }


    @ApiOperation(value="Debug Write Block Profile")
    @RequestMapping(value="writeBlockProfile")
    public ApiResponse writeBlockProfile(
            HttpServletRequest request,
            @RequestParam(value="fileName",required=false,defaultValue="") String fileName) throws ApiException {
        return service.writeBlockProfile(request,fileName);
    }


    @ApiOperation(value="Debug Write Mem Profile")
    @RequestMapping(value="writeMemProfile")
    public ApiResponse writeMemProfile(
            HttpServletRequest request,
            @RequestParam(value="fileName",required=false,defaultValue="") String fileName) throws ApiException {
        return service.writeMemProfile(request,fileName);
    }


    @ApiOperation(value="Debug Start PProf")
    @RequestMapping(value="startPProf")
    public ApiResponse startPProf(
            HttpServletRequest request,
            @RequestParam(value="host",required=false,defaultValue="") String host,
            @RequestParam(value="port",required=false,defaultValue="0") int port) throws ApiException {
        return service.startPProf(request,host,port);
    }


    @ApiOperation(value="Debug Stop PProf")
    @RequestMapping(value="stopPProf")
    public ApiResponse stopPProf(HttpServletRequest request) throws ApiException {
        return service.stopPProf(request);
    }


    @ApiOperation(value="Debug Is PProf Running")
    @RequestMapping(value="isPProfRunning")
    public ApiResponse isPProfRunning(HttpServletRequest request) throws ApiException {
        return service.isPProfRunning(request);
    }


    @ApiOperation(value="Debug Metrics")
    @RequestMapping(value="metrics")
    public ApiResponse metrics(
            HttpServletRequest request,
            @RequestParam(value="isRaw",required=false,defaultValue="true") boolean isRaw) throws ApiException {
        return service.metrics(request,isRaw);
    }


    @ApiOperation(value="Debug Print Block")
    @RequestMapping(value="printBlock")
    public ApiResponse printBlock(
            HttpServletRequest request,
            @RequestParam(value="blockNumber",required=false,defaultValue="0") BigInteger blockNumber) throws ApiException {
        return service.printBlock(request,blockNumber);
    }


    @ApiOperation(value="Debug Pre Image")
    @RequestMapping(value="preImage")
    public ApiResponse preImage(
            HttpServletRequest request,
            @RequestParam(value="sha3hash",required=false,defaultValue="") String sha3hash) throws ApiException {
        return service.preImage(request,sha3hash);
    }


    @ApiOperation(value="Debug Free OS Memory")
    @RequestMapping(value="freeOSMemory")
    public ApiResponse freeOSMemory(HttpServletRequest request) throws ApiException {
        return service.freeOSMemory(request);
    }


    @ApiOperation(value="Debug Set GC Percent")
    @RequestMapping(value="setGCPercent")
    public ApiResponse setGCPercent(
            HttpServletRequest request,
            @RequestParam(value="percent",required=false,defaultValue="0") int percent) throws ApiException {
        return service.setGCPercent(request,percent);
    }


    @ApiOperation(value="Debug Trace Block")
    @RequestMapping(value="traceBlock")
    public ApiResponse traceBlock(
            HttpServletRequest request,
            @RequestParam(value="blockRlp",required=false,defaultValue="") String blockRlp,
            @RequestParam(value="tracerName",required=false,defaultValue="") String tracerName) throws ApiException {
        return service.traceBlock(request,blockRlp,tracerName);
    }


    @ApiOperation(value="Debug Trace Block By Number")
    @RequestMapping(value="traceBlockByNumber")
    public ApiResponse traceBlockByNumber(
            HttpServletRequest request,
            @RequestParam(value="blockNumber",required=false,defaultValue="0") String blockNumber,
            @RequestParam(value="tracerName",required=false,defaultValue="") String tracerName) throws ApiException {
        return service.traceBlockByNumber(request,blockNumber,tracerName);
    }


    @ApiOperation(value="Debug Trace Block By Hash")
    @RequestMapping(value="traceBlockByHash")
    public ApiResponse traceBlockByHash(
            HttpServletRequest request,
            @RequestParam(value="blockHash",required=false,defaultValue="") String blockHash,
            @RequestParam(value="tracerName",required=false,defaultValue="") String tracerName) throws ApiException {
        return service.traceBlockByHash(request,blockHash,tracerName);
    }


    @ApiOperation(value="Debug Trace Block From File")
    @RequestMapping(value="traceBlockFromFile")
    public ApiResponse traceBlockFromFile(
            HttpServletRequest request,
            @RequestParam(value="fileName",required=false,defaultValue="") String fileName,
            @RequestParam(value="tracerName",required=false,defaultValue="") String tracerName) throws ApiException {
        return service.traceBlockFromFile(request,fileName,tracerName);
    }


    @ApiOperation(value="Debug Trace Transaction")
    @RequestMapping(value="traceTransaction")
    public ApiResponse traceTransaction(
            HttpServletRequest request,
            @RequestParam(value="transactionHash",required=false,defaultValue="") String transactionHash,
            @RequestParam(value="tracerName",required=false,defaultValue="") String tracerName) throws ApiException {
        return service.traceTransaction(request,transactionHash,tracerName);
    }


    @ApiOperation(value="Debug Trace Bad Block")
    @RequestMapping(value="traceBadBlock")
    public ApiResponse traceBadBlock(
            HttpServletRequest request,
            @RequestParam(value="blockHash",required=false,defaultValue="") String blockHash,
            @RequestParam(value="tracerName",required=false,defaultValue="") String tracerName) throws ApiException {
        return service.traceBadBlock(request,blockHash,tracerName);
    }


    @ApiOperation(value="Debug Standard Trace Bad Block To File")
    @RequestMapping(value="standardTraceBadBlockToFile")
    public ApiResponse standardTraceBadBlockToFile(
            HttpServletRequest request,
            @RequestParam(value="blockHash",required=false,defaultValue="") String blockHash,
            @RequestParam(value="tracerName",required=false,defaultValue="") String tracerName) throws ApiException {
        return service.standardTraceBadBlockToFile(request,blockHash,tracerName);
    }


    @ApiOperation(value="Debug Standard Trace Block To File")
    @RequestMapping(value="standardTraceBlockToFile")
    public ApiResponse standardTraceBlockToFile(
            HttpServletRequest request,
            @RequestParam(value="blockHash",required=false,defaultValue="") String blockHash,
            @RequestParam(value="tracerName",required=false,defaultValue="") String tracerName) throws ApiException {
        return service.standardTraceBlockToFile(request,blockHash,tracerName);
    }
}

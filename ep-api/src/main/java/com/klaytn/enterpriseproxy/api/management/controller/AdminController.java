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
import com.klaytn.enterpriseproxy.api.management.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;


@RestController
@Api("Management Admin RESTFUL API")
@RequestMapping(value="/management/admin/",method=RequestMethod.POST)
public class AdminController {
    @Autowired
    private AdminService service;



    @ApiOperation(value="Admin AddPeer")
    @RequestMapping(value="addPeer")
    public ApiResponse addPeer(
            HttpServletRequest httpServletRequest,
            @RequestParam(value="url",required=false,defaultValue="") String url) throws ApiException {
        return service.addPeer(httpServletRequest,url);
    }


    @ApiOperation(value="Admin RemovePeer")
    @RequestMapping(value="removePeer")
    public ApiResponse removePeer(
            HttpServletRequest request,
            @RequestParam(value="url",required=false,defaultValue="") String url) throws ApiException {
        return service.removePeer(request,url);
    }


    @ApiOperation(value="Admin DataDir")
    @RequestMapping(value="datadir")
    public ApiResponse dataDir(HttpServletRequest request) throws ApiException {
        return service.dataDir(request);
    }


    @ApiOperation(value="Admin nodeInfo")
    @RequestMapping(value="nodeInfo")
    public ApiResponse nodeInfo(HttpServletRequest request) throws ApiException {
        return service.nodeInfo(request);
    }


    @ApiOperation(value="Admin Peers")
    @RequestMapping(value="peers")
    public ApiResponse peers(HttpServletRequest request) throws ApiException {
        return service.peers(request);
    }


    @ApiOperation(value="Admin Start RPC")
    @RequestMapping(value="startRPC")
    public ApiResponse startRPC(
            HttpServletRequest request,
            @RequestParam(value="host",required=false,defaultValue="") String host,
            @RequestParam(value="port",required=false,defaultValue="0") int port,
            @RequestParam(value="cors",required=false,defaultValue="") String cors,
            @RequestParam(value="apis",required=false,defaultValue="klay,net,personal") String[] apis) throws ApiException {
        return service.startRPC(request,host,port,cors,apis);
    }


    @ApiOperation(value="Admin Start WS")
    @RequestMapping(value="startWS")
    public ApiResponse startWS(
            HttpServletRequest request,
            @RequestParam(value="host",required=false,defaultValue="") String host,
            @RequestParam(value="port",required=false,defaultValue="0") int port,
            @RequestParam(value="cors",required=false,defaultValue="") String cors,
            @RequestParam(value="apis",required=false,defaultValue="klay,net,personal") String[] apis) throws ApiException {
        return service.startWS(request,host,port,cors,apis);
    }


    @ApiOperation(value="Admin Stop RPC")
    @RequestMapping(value="stopRPC")
    public ApiResponse stopRPC(HttpServletRequest request) throws ApiException {
        return service.stopRPC(request);
    }


    @ApiOperation(value="Admin Stop WS")
    @RequestMapping(value="stopWS")
    public ApiResponse stopWS(HttpServletRequest request) throws ApiException {
        return service.stopWS(request);
    }


    @ApiOperation(value="Admin Import Chain")
    @RequestMapping(value="importChain")
    public ApiResponse importChain(
            HttpServletRequest request,
            @RequestParam(value="fileName",required=false,defaultValue="") String filename) throws ApiException {
        return service.importChain(request,filename);
    }


    @ApiOperation(value="Admin Export Chain")
    @RequestMapping(value="exportChain")
    public ApiResponse exportChain(
            HttpServletRequest request,
            @RequestParam(value="fileName",required=false,defaultValue="") String filename) throws ApiException {
        return service.exportChain(request,filename);
    }
}

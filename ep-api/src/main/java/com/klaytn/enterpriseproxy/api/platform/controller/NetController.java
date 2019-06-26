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
import com.klaytn.enterpriseproxy.api.platform.service.NetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;


@RestController
@Api("PlatForm Net RESTFUL API")
@RequestMapping(value="/platform/net/",method=RequestMethod.POST)
public class NetController {
    @Autowired
    private NetService service;





    @ApiOperation("Net networkID")
    @RequestMapping(value="networkID")
    public ApiResponse networkID(HttpServletRequest request) throws ApiException {
        return service.networkID(request);
    }


    @ApiOperation("Net Listening")
    @RequestMapping(value="listening")
    public ApiResponse listening(HttpServletRequest request) throws ApiException {
        return service.listening(request);
    }


    @ApiOperation("Peer Count")
    @RequestMapping(value="peerCount")
    public ApiResponse peerCount(HttpServletRequest request) throws ApiException {
        return service.peerCount(request);
    }

    @ApiOperation("Peer Count By Type")
    @RequestMapping(value="peerCountByType")
    public ApiResponse peerCountByType(HttpServletRequest request) throws ApiException {
        return service.peerCountByType(request);
    }
}
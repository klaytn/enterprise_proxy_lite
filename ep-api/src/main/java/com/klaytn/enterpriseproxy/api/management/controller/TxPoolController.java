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
import com.klaytn.enterpriseproxy.api.management.service.TxPoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;


@RestController
@Api("Management TxPool RESTFUL API")
@RequestMapping(value="/management/txpool/",method=RequestMethod.POST)
public class TxPoolController {
    @Autowired
    private TxPoolService service;




    @ApiOperation("TxPool Content")
    @RequestMapping(value="content")
    public ApiResponse content(HttpServletRequest request) throws ApiException {
        return service.content(request);
    }


    @ApiOperation("TxPool Inspect")
    @RequestMapping(value="inspect")
    public ApiResponse inspect(HttpServletRequest request) throws ApiException {
        return service.inspect(request);
    }


    @ApiOperation("TxPool Status")
    @RequestMapping(value="status")
    public ApiResponse status(HttpServletRequest request) throws ApiException {
        return service.status(request);
    }
}
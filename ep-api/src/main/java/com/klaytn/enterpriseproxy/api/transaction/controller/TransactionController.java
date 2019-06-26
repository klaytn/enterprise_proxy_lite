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
package com.klaytn.enterpriseproxy.api.transaction.controller;

import com.klaytn.enterpriseproxy.api.common.exception.ApiException;
import com.klaytn.enterpriseproxy.api.common.model.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.klaytn.enterpriseproxy.api.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;


@RestController
@Api(description="Transaction RESTFUL API")
@RequestMapping(value="/transaction/",method=RequestMethod.POST)
public class TransactionController {
    @Autowired
    private TransactionService service;




    @ApiOperation(value="Signed Transaction")
    @RequestMapping(value="signed")
    public ApiResponse signed(
            HttpServletRequest httpServletRequest,
            @RequestParam(value="rawTransaction",required=false,defaultValue="") String rawTransaction) throws ApiException {
        return service.signed(httpServletRequest,rawTransaction);
    }
}
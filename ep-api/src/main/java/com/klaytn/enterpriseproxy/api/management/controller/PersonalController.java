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
import com.klaytn.enterpriseproxy.api.management.service.PersonalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;


@RestController
@Api("Management Personal RESTFUL API")
@RequestMapping(value="/management/personal/",method=RequestMethod.POST)
public class PersonalController {
    @Autowired
    private PersonalService service;




    @ApiOperation("Personal Import Raw Key")
    @RequestMapping(value="importRawKey")
    public ApiResponse importRawKey(
            HttpServletRequest request,
            @RequestParam(value="keyData",required=false,defaultValue="") String keyData,
            @RequestParam(value="passPhrase",required=false,defaultValue="") String passPhrase) throws ApiException {
        return service.importRawKey(request,keyData,passPhrase);
    }


    @ApiOperation("Personal List Accounts")
    @RequestMapping(value="listAccounts")
    public ApiResponse listAccounts(HttpServletRequest request) throws ApiException {
        return service.listAccounts(request);
    }


    @ApiOperation("Personal Lock Account")
    @RequestMapping(value="lockAccount")
    public ApiResponse lockAccount(
            HttpServletRequest request,
            @RequestParam(value="accountAddress",required=false,defaultValue="") String accountAddress) throws ApiException {
        return service.lockAccount(request,accountAddress);
    }


    @ApiOperation("Personal Unlock Account")
    @RequestMapping(value="unlockAccount")
    public ApiResponse unlockAccount(
            HttpServletRequest request,
            @RequestParam(value="accountAddress",required=false,defaultValue="") String accountAddress,
            @RequestParam(value="passPhrase",required=false,defaultValue="") String passPhrase,
            @RequestParam(value="duration",required=false,defaultValue="0") int duration) throws ApiException {
        return service.unlockAccount(request,accountAddress,passPhrase,duration);
    }


    @ApiOperation("Personal Send Transaction")
    @RequestMapping(value="sendTransaction")
    public ApiResponse sendTransaction(
            HttpServletRequest request,
            @RequestParam(value="fromAddress",required=false,defaultValue="") String fromAddress,
            @RequestParam(value="toAddress",required=false,defaultValue="") String toAddress,
            @RequestParam(value="gas",required=false,defaultValue="") String gas,
            @RequestParam(value="gasPrice",required=false,defaultValue="") String gasPrice,
            @RequestParam(value="value",required=false,defaultValue="") String value,
            @RequestParam(value="data",required=false,defaultValue="") String data,
            @RequestParam(value="nonce",required=false,defaultValue="") String nonce,
            @RequestParam(value="passPhrase",required=false,defaultValue="") String passPhrase) throws ApiException {
        return service.sendTransaction(request,fromAddress,toAddress,gas,gasPrice,value,data,nonce, passPhrase);
    }


    @ApiOperation("Personal Sign")
    @RequestMapping(value="sign")
    public ApiResponse sign(
            HttpServletRequest request,
            @RequestParam(value="message",required=false,defaultValue="") String message,
            @RequestParam(value="accountAddress",required=false,defaultValue="") String accountAddress,
            @RequestParam(value="passPhrase",required=false,defaultValue="") String passPhrase) throws ApiException {
        return service.sign(request,message,accountAddress,passPhrase);
    }


    @ApiOperation("Personal ecrecover")
    @RequestMapping(value="ecRecover")
    public ApiResponse ecRecover(
            HttpServletRequest request,
            @RequestParam(value="message",required=false,defaultValue="") String message,
            @RequestParam(value="signature",required=false,defaultValue="") String signature) throws ApiException {
        return service.ecRecover(request,message,signature);
    }
}

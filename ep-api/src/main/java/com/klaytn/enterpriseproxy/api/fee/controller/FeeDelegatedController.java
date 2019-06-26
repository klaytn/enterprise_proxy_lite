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
package com.klaytn.enterpriseproxy.api.fee.controller;

import com.klaytn.enterpriseproxy.api.fee.service.FeeDelegatedSmartContractService;
import com.klaytn.enterpriseproxy.api.fee.service.FeeDelegatedValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.klaytn.enterpriseproxy.api.common.exception.ApiException;
import com.klaytn.enterpriseproxy.api.common.model.ApiResponse;
import com.klaytn.enterpriseproxy.api.fee.service.FeeDelegatedAccountService;
import com.klaytn.enterpriseproxy.api.fee.service.FeeDelegatedCancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;


@RestController
@Api("Fee Delegated Transaction RESTFUL API")
@RequestMapping(value="/feeDelegated/",method= RequestMethod.POST)
public class FeeDelegatedController {
    @Autowired
    private FeeDelegatedValueService value;

    @Autowired
    private FeeDelegatedAccountService account;

    @Autowired
    private FeeDelegatedCancelService cancel;

    @Autowired
    private FeeDelegatedSmartContractService smartContract;



    
    @ApiOperation(value="Tx Type Fee Delegated Value Transfer")
    @RequestMapping(value="valueTransfer")
    public ApiResponse valueTransfer(
            HttpServletRequest httpServletRequest,
            @RequestParam(value="chainId",required=false,defaultValue="1001") int chainId,
            @RequestParam(value="rawTransaction",required=false,defaultValue="") String rawTransaction) throws ApiException {
        return value.transfer(httpServletRequest,chainId,rawTransaction);
    }


    @ApiOperation(value="Tx Type Fee Delegated Value Transfer With Ratio")
    @RequestMapping(value="valueTransferWithRatio")
    public ApiResponse valueTransferWithRatio(
            HttpServletRequest httpServletRequest,
            @RequestParam(value="chainId",required=false,defaultValue="1001") int chainId,
            @RequestParam(value="rawTransaction",required=false,defaultValue="") String rawTransaction) throws ApiException {
        return value.transferWithRatio(httpServletRequest,chainId,rawTransaction);
    }


    @ApiOperation(value="Tx Type Fee Delegated Value Transfer Memo")
    @RequestMapping(value="valueTransferMemo")
    public ApiResponse valueTransferMemo(
            HttpServletRequest httpServletRequest,
            @RequestParam(value="chainId",required=false,defaultValue="1001") int chainId,
            @RequestParam(value="rawTransaction",required=false,defaultValue="") String rawTransaction) throws ApiException {
        return value.transferMemo(httpServletRequest,chainId,rawTransaction);
    }


    @ApiOperation(value="Tx Type Fee Delegated Value Transfer Memo With Ratio")
    @RequestMapping(value="valueTransferMemoWithRatio")
    public ApiResponse valueTransferMemoWithRatio(
            HttpServletRequest httpServletRequest,
            @RequestParam(value="chainId",required=false,defaultValue="1001") int chainId,
            @RequestParam(value="rawTransaction",required=false,defaultValue="") String rawTransaction) throws ApiException {
        return value.transferMemoWithRatio(httpServletRequest,chainId,rawTransaction);
    }


    @ApiOperation(value="Tx Type Fee Delegated Account Update")
    @RequestMapping(value="accountUpdate")
    public ApiResponse accountUpdate(
            HttpServletRequest httpServletRequest,
            @RequestParam(value="chainId",required=false,defaultValue="1001") int chainId,
            @RequestParam(value="rawTransaction",required=false,defaultValue="") String rawTransaction) throws ApiException {
        return account.update(httpServletRequest,chainId,rawTransaction);
    }


    @ApiOperation(value="Tx Type Fee Delegated Account Update With Ratio")
    @RequestMapping(value="accountUpdateWithRatio")
    public ApiResponse accountUpdateWithRatio(
            HttpServletRequest httpServletRequest,
            @RequestParam(value="chainId",required=false,defaultValue="1001") int chainId,
            @RequestParam(value="rawTransaction",required=false,defaultValue="") String rawTransaction) throws ApiException {
        return account.updateWithRatio(httpServletRequest,chainId,rawTransaction);
    }


    @ApiOperation(value="Tx Type Fee Delegated Cancel")
    @RequestMapping(value="cancel")
    public ApiResponse cancel(
            HttpServletRequest httpServletRequest,
            @RequestParam(value="chainId",required=false,defaultValue="1001") int chainId,
            @RequestParam(value="rawTransaction",required=false,defaultValue="") String rawTransaction) throws ApiException {
        return cancel.cancel(httpServletRequest,chainId,rawTransaction);
    }


    @ApiOperation(value="Tx Type Fee Delegated Cancel With Ratio")
    @RequestMapping(value="cancelWithRatio")
    public ApiResponse cancelWithRatio(
            HttpServletRequest httpServletRequest,
            @RequestParam(value="chainId",required=false,defaultValue="1001") int chainId,
            @RequestParam(value="rawTransaction",required=false,defaultValue="") String rawTransaction) throws ApiException {
        return cancel.cancelWithRatio(httpServletRequest,chainId,rawTransaction);
    }


    @ApiOperation(value="Tx Type Fee Delegated SmartContract Deploy")
    @RequestMapping(value="smartContractDeploy")
    public ApiResponse smartContractDeploy(
            HttpServletRequest httpServletRequest,
            @RequestParam(value="chainId",required=false,defaultValue="1001") int chainId,
            @RequestParam(value="rawTransaction",required=false,defaultValue="") String rawTransaction) throws ApiException {
        return smartContract.deploy(httpServletRequest,chainId,rawTransaction);
    }


    @ApiOperation(value="Tx Type Fee Delegated SmartContract Deploy With Ratio")
    @RequestMapping(value="smartContractDeployWithRatio")
    public ApiResponse smartContractDeployWithRatio(
            HttpServletRequest httpServletRequest,
            @RequestParam(value="chainId",required=false,defaultValue="1001") int chainId,
            @RequestParam(value="rawTransaction",required=false,defaultValue="") String rawTransaction) throws ApiException {
        return smartContract.deployWithRatio(httpServletRequest,chainId,rawTransaction);
    }


    @ApiOperation(value="Tx Type Fee Delegated SmartContract Execution")
    @RequestMapping(value="smartContractExecution")
    public ApiResponse smartContractExecution(
            HttpServletRequest httpServletRequest,
            @RequestParam(value="chainId",required=false,defaultValue="1001") int chainId,
            @RequestParam(value="rawTransaction",required=false,defaultValue="") String rawTransaction) throws ApiException {
        return smartContract.execution(httpServletRequest,chainId,rawTransaction);
    }


    @ApiOperation(value="Tx Type Fee Delegated SmartContract Execution With Ratio")
    @RequestMapping(value="smartContractExecutionWithRatio")
    public ApiResponse smartContractExecutionWithRatio(
            HttpServletRequest httpServletRequest,
            @RequestParam(value="chainId",required=false,defaultValue="1001") int chainId,
            @RequestParam(value="rawTransaction",required=false,defaultValue="") String rawTransaction) throws ApiException {
        return smartContract.executionWithRatio(httpServletRequest,chainId,rawTransaction);
    }
}
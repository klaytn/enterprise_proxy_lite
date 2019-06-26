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
package com.klaytn.enterpriseproxy.rpc.common.util;

import com.klaytn.enterpriseproxy.rpc.common.model.*;
import com.klaytn.enterpriseproxy.utils.ArrayUtil;
import com.klaytn.enterpriseproxy.utils.ObjectUtil;
import com.klaytn.enterpriseproxy.utils.StringUtil;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;


public class RpcUtils {
    /**
     * JSON RPC REQUEST Made
     *
     * @param rpcMethods
     * @param params
     * @param id
     * @return
     */
    public static RpcRequest madeRequest(RpcMethods rpcMethods,Object[] params,int id) {
        RpcRequest request = new RpcRequest();
        request.setMethod(rpcMethods.getValue());
        request.setParams(params);
        request.setId(id);
        return request;
    }


    /**
     * JSON RPC REQUEST Made
     *
     * @param rpcMethods
     * @return
     */
    public static RpcRequest madeRequest(RpcMethods rpcMethods) {
        Object[] params = {};
        return madeRequest(rpcMethods,params);
    }


    /**
     * JSON RPC REQUEST Made
     *
     * @param rpcMethods
     * @param params
     * @return
     */
    public static RpcRequest madeRequest(RpcMethods rpcMethods, Object[] params) {
        return madeRequest(rpcMethods,params,1);
    }


    /**
     * Management API Parameter set and get
     *
     * @param targetHost
     * @return
     */
    public static Map<String,Object> getMadeManagementParameter(String targetHost) {
        if (StringUtil.isEmpty(targetHost)) {
            return null;
        }

        String splitStr[] = StringUtil.split(targetHost,":");
        if (ArrayUtil.isEmpty(splitStr)) {
            return null;
        }

        String host = splitStr[0];
        String port = splitStr[1];

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("host",host);
        params.put("port",port);
        return params;
    }


    /**
     * KlayCall Model Set
     *
     * @param from
     * @param to
     * @param gas
     * @param gasPrice
     * @param value
     * @param data
     * @param nonce
     * @return
     */
    public static KlayCall setKlayCall(String from,String to,String gas,String gasPrice,String value,String data,String nonce) {
        KlayCall klayCall = new KlayCall();
        klayCall.setFrom(from);
        klayCall.setTo(to);
        klayCall.setGas(gas);
        klayCall.setGasPrice(gasPrice);
        klayCall.setValue(value);
        klayCall.setData(data);
        klayCall.setNonce(nonce);
        return klayCall;
    }


    /**
     * KlayCall Model Set
     *
     * @param from
     * @param to
     * @param gas
     * @param gasPrice
     * @param value
     * @param data
     * @return
     */
    public static KlayCall setKlayCall(String from,String to,String gas,String gasPrice,String value,String data) {
        return setKlayCall(from,to,gas,gasPrice,value,data,"");
    }


    /**
     * FilterOptions Model Set
     *
     * @param fromBlock
     * @param toBlock
     * @param address
     * @param topics
     * @param blockHash
     * @return
     */
    public static FilterOptions setFilterOptions(String fromBlock,String toBlock,String[] address,String[] topics,String blockHash) {
        FilterOptions filterOptions = new FilterOptions();
        filterOptions.setFromBlock(fromBlock);
        filterOptions.setToBlock(toBlock);
        filterOptions.setAddress(address);
        filterOptions.setTopics(topics);
        filterOptions.setBlockHash(blockHash);
        return filterOptions;
    }


    /**
     * FilterOptions Model Set
     *
     * @param fromBlock
     * @param toBlock
     * @param address
     * @param topics
     * @return
     */
    public static FilterOptions setFilterOptions(String fromBlock,String toBlock,String[] address,String[] topics) {
        return setFilterOptions(fromBlock,toBlock,address,topics,"");
    }


    /**
     * Tracer Model Set
     *
     * @param tracerName
     * @return
     */
    public static TracerCall setTracerCall(String tracerName) {
        TracerCall tracerCall = new TracerCall();
        tracerCall.setTracer(tracerName);
        return tracerCall;
    }


    /**
     * is Valid BigInteger
     *
     * @param bigInteger
     * @return
     */
    public static boolean isValidBiginteger(BigInteger bigInteger) {
        if (ObjectUtil.isEmpty(bigInteger)) {
            return false;
        }

        if (BigInteger.ZERO.equals(bigInteger)) {
            return false;
        }

        return true;
    }


    private RpcUtils() {
        throw new IllegalStateException("Utility Class");
    }
}
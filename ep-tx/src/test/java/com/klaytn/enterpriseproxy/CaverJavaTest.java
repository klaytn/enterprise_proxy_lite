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
package com.klaytn.enterpriseproxy;

import com.klaytn.caver.Caver;
import com.klaytn.caver.methods.response.Quantity;
import com.klaytn.caver.utils.Convert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.*;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;


public class CaverJavaTest {
    private static final Logger logger = LoggerFactory.getLogger(CaverJavaTest.class);

    static final BigInteger GAS_PRICE= Convert.toPeb("25", Convert.Unit.STON).toBigInteger();
    static final BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);

    static final Credentials LUMAN = Credentials.create(
            "0xd0fdd0999ca4dac89be8658c8a9a51a719c6f03060649bc39a3ac335b837a9b1",
            "0xe7e2367aff158ae5a7abf864f155e7461867be19f6ef244b6a5b19c0a92d872fac729861c3618e5633f25137eee9314414b096ef4a9be7eab5edbb354a6a3171"
    );

    static final Credentials WAYNE = Credentials.create(
            "0xe60062637f5760b9b410f77893dfcb41e4e8fb470d709fcc23d3a548863e909c",
            "0xf26c8bd68121201f1e0c94c1760e8479966629b1082e018f89bf4758176cc0c1a94e6a045f7a1ca38234d175e8d3c45f311422256178c7c47d611390c0b23d66"
    );

    private Caver caverj;



    @Before
    public void setUp() {
        this.caverj = Caver.build(Caver.BAOBAB_URL);
    }


    @Test
    public void CAVERJ_TEST_NO1() throws Exception {
//        String result = caverj.klay().getNodeInfo().send().getResult();
//        logger.info("@@@@ GET NODE INFO : " + result.toString() + " @@@@");

//        result = caverj.klay().getGasPrice().send().getResult();
//        logger.info("@@@@ GET GAS PRICE : " + result.toString() + " @@@@");

        List accountList = caverj.klay().getAccounts().send().getResult();
        logger.info("@@@@ ACCOUNT LIST : " + accountList.toString() + " @@@@");
    }


    @Test
    public void TRANSACTION_TEST() throws Exception {
        RawTransaction rawTransaction = this._createTransaction();
        byte[] encoded =TransactionEncoder.encode(rawTransaction);
        byte[] hashed = Hash.sha3(encoded);

        String hash = Numeric.toHexString(hashed);
        logger.info("@@@@ HASHED " + hash + " @@@@");

        logger.info("@@@@ DECODE HASHED " + TransactionDecoder.decode(hash) + " @@@@");
    }


    @Test
    public void DECODING_TEST() throws Exception {
        BigInteger nonce = BigInteger.ZERO;
        BigInteger gasPrice = BigInteger.ONE;
        BigInteger gasLimit = BigInteger.TEN;
        String to = "0x0add5355";
        BigInteger value = BigInteger.valueOf(Long.MAX_VALUE);
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                nonce,
                gasPrice,
                gasLimit,
                to,
                value
        );

        byte[] encodedMessage = TransactionEncoder.encode(rawTransaction);
        String hexMesssage = Numeric.toHexString(encodedMessage);
        logger.info("@@@ HEX MESSAGE : " + hexMesssage + " @@@");

        RawTransaction result = TransactionDecoder.decode(hexMesssage);
        logger.info("@@@ HEX DECODE : " + result.getData() + " @@@");
    }


    private RawTransaction _createTransaction() throws Exception {
        BigInteger value = Convert.toPeb("0.5", Convert.Unit.KLAY).toBigInteger();
//        return (RawTransaction) RawTransaction.createEtherTransaction(
//                this._getNonce(LUMAN.getAddress()),
//                GAS_PRICE,
//                GAS_LIMIT,
//                WAYNE.getAddress(),
//                value
//        );
        return null;
    }


    private Quantity _getNonce(String address) throws Exception {
        return caverj.klay()
                .getTransactionCount(address,DefaultBlockParameterName.LATEST)
                .sendAsync()
                .get();
    }
}

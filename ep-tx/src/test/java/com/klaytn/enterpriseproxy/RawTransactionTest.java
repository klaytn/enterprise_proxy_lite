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


import com.klaytn.enterpriseproxy.tx.model.TransactionResponse;
import com.klaytn.enterpriseproxy.tx.transaction.RawTransaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@SpringBootTest
@SpringBootConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class RawTransactionTest {
    private static final Logger logger = LoggerFactory.getLogger(RawTransactionTest.class);

    @Autowired
    private RawTransaction rawTransaction;



    @Test
    public void RAW_TRANSACTION_TEST() throws Exception {
        String rawTxHash = "0xf8680c8505d21dba00825208949f1e42a7fe2216dd2ef06b51fedc5a770d3b69bc8203e8808207f2a092009e14c8f89ec2741a74b9e95bab0aca198e8e4d7499682393bf774ed8297ca0209109ccfcee207fdd977efac191403c7ce4592c33c730a4347c9ca68f3b9ef9";
        TransactionResponse response = rawTransaction.send("",rawTxHash);
        logger.info("@@@@ RESPONSE : " + response + "@@@@");
    }


    @SpringBootApplication
    static class init {

    }
}
/*
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
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.klaytn.enterpriseproxy.fee;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestEncryptor {
    private static final Logger logger = LoggerFactory.getLogger(TestEncryptor.class);
    private static final String ENCRYPT_KEY = "@klaytnep@";


    @Test
    public void ENCRYPTOR_TEST() throws Exception {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(ENCRYPT_KEY);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);

        String feePayerAddress = encryptor.encrypt("0xd957ea817092c781cd52f51f64227bff8fec1802");
        logger.info("@@@@ FEE PAYER ADDRESS ENCRYPT : " + feePayerAddress + " @@@@");
        logger.info("@@@@ FEE PAYER ADDRESS DECRYPT : " + encryptor.decrypt(feePayerAddress) + " @@@@");

        String feePayerPassword = encryptor.encrypt("password");
        logger.info("@@@@ FEE PAYER PASSWORD ENCRYPT : " + feePayerPassword + " @@@@");
        logger.info("@@@@ FEE PAYER PASSWORD DECRYPT : " + encryptor.decrypt(feePayerPassword) + " @@@@");

        String feePayerKeystoreFilePath = encryptor.encrypt("/Users/funny/Library/Klaytn/baobab/keystore/UTC--2019-05-14T02-51-58.330000000Z--0xd957ea817092c781cd52f51f64227bff8fec1802.json");
        logger.info("@@@@ FEE PAYER KEY STORE FILE PATH ENCRYPT : " + feePayerKeystoreFilePath + " @@@@");
        logger.info("@@@@ FEE PAYER KEY STORE FILE PATH DECRYPT : " + encryptor.decrypt(feePayerKeystoreFilePath) + " @@@@");
    }
}

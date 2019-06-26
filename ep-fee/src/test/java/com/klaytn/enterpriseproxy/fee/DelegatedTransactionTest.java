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
package com.klaytn.enterpriseproxy.fee;

import com.klaytn.caver.crpyto.KlayCredentials;
import com.klaytn.caver.tx.model.KlayRawTransaction;
import com.klaytn.caver.tx.type.TxType;
import com.klaytn.caver.tx.type.TxTypeFeeDelegatedValueTransfer;
import com.klaytn.caver.wallet.KlayWalletUtils;
import com.klaytn.enterpriseproxy.fee.config.FeePayerProperties;
import com.klaytn.enterpriseproxy.fee.delegated.Account;
import com.klaytn.enterpriseproxy.fee.model.FeeDelegatedParams;
import com.klaytn.enterpriseproxy.tx.model.TransactionResponse;
import com.klaytn.enterpriseproxy.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import com.klaytn.enterpriseproxy.fee.config.TestJasyptConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.web3j.rlp.RlpType;
import org.web3j.utils.Numeric;

import java.io.File;
import java.math.BigInteger;


@SpringBootConfiguration
@Import(TestJasyptConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = "classpath:application.properties")
public class DelegatedTransactionTest {
    private static final Logger logger = LoggerFactory.getLogger(DelegatedTransactionTest.class);

    @Autowired
    private FeePayerProperties properties;

    @Autowired
    private Account account;


    static final KlayCredentials TESTER = KlayCredentials.create(
            "0xf8cc7c3813ad23817466b1802ee805ee417001fcce9376ab8728c92dd8ea0a6b"
    );


    static final KlayCredentials PAYER = KlayCredentials.create(
            "0xb9d5558443585bca6f225b935950e3f6e69f9da8a5809a83f51c3365dff53936"
    );


    @Test
    public void FEE_DELEGATED_TEST() throws Exception {
        // On the client, create a rawTransaction using Caver-Java TxTypeFeeDelegatedValueTransfer.
        TxTypeFeeDelegatedValueTransfer tx = TxTypeFeeDelegatedValueTransfer.createTransaction(
                BigInteger.valueOf(2),
                BigInteger.valueOf(0x19),
                BigInteger.valueOf(0x3b9ac9ff),
                TESTER.getAddress(),
                BigInteger.valueOf(0x989680),
                "0x75c3098Be5E4B63FBAc05838DaAEE378dD48098d"
        );

        KlayRawTransaction transaction = tx.sign(TESTER, 1);
        String rawTxHash = transaction.getValueAsString();
        logger.info("@@@@ RAW TX HASH : " + rawTxHash + " @@@@");

        byte[] rawTxByteArray = transaction.getValue();
        TxTypeFeeDelegatedValueTransfer transfer = TxTypeFeeDelegatedValueTransfer.decodeFromRawTransaction(rawTxByteArray);

        logger.info("@@@@ FEE DELEGATED VALUE TRANSFER DECODE RLP VALUES @@@@");
        for (RlpType rlpType : transfer.rlpValues()) {
            logger.info("@@ RLPTYPE : " + rlpType + " @@");
        }
    }


    @Test
    public void FEE_DELEGATED_RAW_TX_HASH_TEST() throws Exception {
        String rawTxHash = "0x09f87c0219843b9ac9ff9475c3098be5e4b63fbac05838daaee378dd48098d839896809490b3e9a3770481345a7f17f22f16d020bccfd33ef845f84325a089bb7f66d87c72554beb2f4b9255f864a92866476932793ed755e95164e1679fa075fb8f74bf6f1ff35b15d889bc533502ce0dc68a0a0e1f106c566086b647530b";
        String txType = StringUtils.substring(rawTxHash,0,4);
        byte[] txTypeBytes = Numeric.hexStringToByteArray(txType);
        byte txTypeByte = txTypeBytes[0];

        logger.info("@@@@ TXTYPE : " + TxType.Type.findByValue(txTypeByte) + " @@@@");

        logger.info("@@@@ TX TYPE BYTES : " + txTypeBytes[0] + " @@@@");
        logger.info("@@@@ BYTES TO STRING : " + Numeric.toHexString(txTypeBytes) + " @@@@");

        BigInteger quantity = Numeric.decodeQuantity(StringUtils.substring(rawTxHash,0,4));

        logger.info("@@@@ QUANTITY : " + quantity + " @@@@");
        logger.info("@@@@ ENCODE : " + Numeric.encodeQuantity(quantity) + " @@@@");


        logger.info("@@@@ HEAD : " + StringUtils.substring(rawTxHash,0,4) + " @@@@");
        logger.info("@@@@ TYPE : " + StringUtils.substring(rawTxHash,2,4) + " @@@@");


        TxTypeFeeDelegatedValueTransfer transfer = TxTypeFeeDelegatedValueTransfer.decodeFromRawTransaction(rawTxHash);
        logger.info("@@@@ TRANSFER TX TYPE : " + transfer.getType() + " @@@@");
    }


    @Test
    public void CREDENTIALS_TEST() throws Exception {
        String feePayerAddress = properties.getAddress();
        String feePayerPassword = properties.getPassword();
        String feePayerKeyStoreFilePath = properties.getKeyStoreFilePath();

        logger.info("@@@@ FEE PAYER ADDRESS : " + feePayerAddress + " @@@@");
        logger.info("@@@@ FEE PAYER PASSWORD : " + feePayerPassword + " @@@@");
        logger.info("@@@@ KEY STORE FILE PATH : " + feePayerKeyStoreFilePath + " @@@@");

        // 1. wallet file create
        if (StringUtil.isEmpty(feePayerKeyStoreFilePath)) {
            String keyDirectory = KlayWalletUtils.getBaobabKeyDirectory();
            File destinationDir = new File(keyDirectory);
            String keyStoreFilePath = KlayWalletUtils.generateNewWalletFile(feePayerAddress,feePayerPassword,destinationDir);
            logger.info("@@@@ KEY STORE FILE PATH : " + keyStoreFilePath + " @@@@");
        }

        // 2. wallet file is exist
        KlayCredentials credentials = KlayWalletUtils.loadCredentials("<USER INPUT PASSWORD>","<USER INPUT KEY STORE FILE PATH>");
        logger.info("@@@@ CREDENTIALS ADDRESS : " + credentials.getAddress() + " @@@@");
        logger.info("@@@@ CREDENTIALS ECKEYPAIR : " + credentials.getEcKeyPair() + " @@@@");
    }


    @Test
    public void CAVERJ_TX_HASH_CREATE_TEST() throws Exception {
        TxTypeFeeDelegatedValueTransfer transfer = TxTypeFeeDelegatedValueTransfer.createTransaction(
                BigInteger.valueOf(0),
                BigInteger.valueOf(0x19),
                BigInteger.valueOf(0x3b9ac9ff),
                "0x720ab1170B970896a6d087312ac39ae9F041E2f4",
                BigInteger.valueOf(0x989680),
                "0xd48215b6de3d9d13642bb38667dc60712235f870"
        );

        logger.info("@@@@ TRANSFER TYPE : " + transfer.getType() + " @@@@");
    }


    @Test
    public void FEE_DELEGATED_ASPECT_TEST() {
        FeeDelegatedParams params = new FeeDelegatedParams();
        params.setChainId(101);
        params.setTargetHost("https://api.baobab.klaytn.net:8651");
        params.setRawTransactionHash("0x");

        TransactionResponse result = account.update(params);
        logger.info("@@@@ RESULT :  " + result + " @@@@");
    }


    @SpringBootApplication
    static class init {

    }
}

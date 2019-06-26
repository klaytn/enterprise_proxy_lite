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
package com.klaytn.enterpriseproxy.common;

import com.klaytn.enterpriseproxy.utils.StringUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CommonTest {
    private static final Logger logger = LoggerFactory.getLogger(CommonTest.class);


    @Test
    public void OBJECTUTIL_TEST() throws Exception {
        String test = "1111";
        logger.info(String.valueOf(Integer.parseInt(test)));
    }


    @Test
    public void HEXTODECIMAL_TEST() throws Exception {
        String hex = "0x5a9";
        logger.info("@@@@ IS HEX : " + StringUtil.isHexadecimal(hex) + " @@@@");
        logger.info("@@@@ HEX TO DECIMAL : " + StringUtil.toString(StringUtil.hexToBigInteger(hex)) + " @@@@");
    }
}
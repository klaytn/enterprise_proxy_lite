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
package com.klaytn.enterpriseproxy.fee.config;

import net.sf.json.JSONSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;


@Component
@EnableConfigurationProperties
@ConfigurationProperties(value="feepayer",ignoreUnknownFields=true)
public class FeePayerProperties implements Serializable {
    private static final long serialVersionUID = -3746968118012353633L;

    /**
     * FeePayer Address
     * @type string
     */
    private String address;


    /**
     * FeePayer Passsword
     * @type string
     */
    private String password;


    /**
     * FeePayer KeyStoreFilePath
     * @type string
     */
    private String keyStoreFilePath;

    


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKeyStoreFilePath() {
        return keyStoreFilePath;
    }

    public void setKeyStoreFilePath(String keyStoreFilePath) {
        this.keyStoreFilePath = keyStoreFilePath;
    }

    @Override
    public String toString() {
        return JSONSerializer.toJSON(this).toString();
    }
}
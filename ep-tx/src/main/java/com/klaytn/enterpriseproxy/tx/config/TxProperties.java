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
package com.klaytn.enterpriseproxy.tx.config;

import net.sf.json.JSONSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;


@Component
@EnableConfigurationProperties
@ConfigurationProperties(value="klaytn.rpc.server",ignoreUnknownFields=true)
public class TxProperties implements Serializable {
    private static final long serialVersionUID=-5978293391451565915L;


    /**
     * caverj klaytn server host
     * @type string
     */
    private String host;


    /**
     * caverj klaytn server port
     * @type number
     */
    private int port;





    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host=host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port=port;
    }

    @Override
    public String toString() {
        return JSONSerializer.toJSON(this).toString();
    }
}
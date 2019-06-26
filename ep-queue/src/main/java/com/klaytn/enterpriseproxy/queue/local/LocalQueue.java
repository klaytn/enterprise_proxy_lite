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
package com.klaytn.enterpriseproxy.queue.local;

import com.klaytn.enterpriseproxy.utils.CollectionUtil;
import com.klaytn.enterpriseproxy.utils.MapUtil;
import com.klaytn.enterpriseproxy.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class LocalQueue<T> implements Local<T> {
    private static final Logger logger = LoggerFactory.getLogger(LocalQueue.class);

    
    /**
     * local queue key
     * @type string;
     */
    private String queueKey;


    /**
     * local queue storage
     * @type concurrentHashMap (thread safe)
     */
    private static ConcurrentHashMap queueStorage;




    public LocalQueue(String key) {
        this.queueKey = key;
        this._createStorage();
    }


    /**
     * set queue storage
     * @private
     */
    private void _createStorage() {
        if (MapUtil.isEmpty(queueStorage)) {
            queueStorage = CollectionUtil.createConcurrentMap();
        }
    }


    /**
     * add queue by key
     *
     * @param value
     */
    public void add(T value) {
        if (this._isKey(this.queueKey)) {
            this._getQueueByKey(this.queueKey).add(value);
        }

        else {
            List<T> valueList = this._getQueueByKey(null);
            valueList.add(value);

            queueStorage.put(this.queueKey,valueList);
        }

        logger.info("@@@@ QUEUE ADD [" + this.queueKey + "] @@@@");
    }


    /**
     * get queue size
     *
     * @return
     */
    public long getQueueSize() {
        return this._getQueueByKey(this.queueKey).size();
    }


    /**
     * get queue list by key
     *
     * @return
     */
    public List<T> getQueueList() {
        return this._getQueueByKey(this.queueKey);
    }


    /**
     * clear queue by key
     *
     * @void
     */
    public void clearQueue() {
        this._getQueueByKey(this.queueKey).clear();
        logger.info("@@@@ QUEUE CLEAR [" + this.queueKey + "] @@@@");
    }


    /**
     * get queue list by key
     *
     * @param key
     * @return
     */
    private List<T> _getQueueByKey(String key) {
        List<T> queue = CollectionUtil.createCopyOnWriteArrayList();
        if (MapUtil.isEmpty(queueStorage)) {
            return queue;
        }

        return this._isKey(key) ? (List<T>) queueStorage.get(key) : queue;
    }


    /**
     * is key exist
     *
     * @param key
     * @return
     */
    private boolean _isKey(String key) {
        if (MapUtil.isEmpty(queueStorage)) {
            return false;
        }

        if (StringUtil.isEmpty(key)) {
            return false;
        }

        return queueStorage.containsKey(key);
    }
}
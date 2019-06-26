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
package com.klaytn.enterpriseproxy.queue;

import com.klaytn.enterpriseproxy.queue.local.LocalQueue;
import com.klaytn.enterpriseproxy.utils.SchedulerAbstract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;


@Component
public class QueueScheduler extends SchedulerAbstract {
    private static final Logger logger = LoggerFactory.getLogger(QueueScheduler.class);
    private LocalQueue localQueue;



    @PostConstruct
    public void init() {
        localQueue = new LocalQueue("TESTKEY");
    }


    @Override
    public void runner() {
        logger.info("@@@@ QUEUE SIZE : " + localQueue.getQueueSize() + " @@@@");
        logger.info("@@@@ THREAD : " + Thread.activeCount()  + " @@@@");

        for (int i=0;i<1000;i++) {
            localQueue.add("ADD NUM : " + i);
        }

        long localQueueSize = localQueue.getQueueSize();
        if (localQueueSize > 1000) {
            logger.info("@@@@ LOCAL QUEUE CLEAR @@@@");
            localQueue.clearQueue();
        }
    }


    @Override
    public Trigger getTrigger() {
        return new PeriodicTrigger(1,TimeUnit.SECONDS);
    }
}
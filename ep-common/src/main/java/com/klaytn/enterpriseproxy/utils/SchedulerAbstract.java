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
package com.klaytn.enterpriseproxy.utils;


import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;


public abstract class SchedulerAbstract {
    /**
     * Thread Pool Task Scheduler
     * 
     * @private
     */
    private ThreadPoolTaskScheduler scheduler;




    /**
     * stop scheduler
     *
     * @void
     */
    public void stop() {
        scheduler.destroy();
    }


    /**
     * start
     *
     * @void
     */
    public void start() {
        scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(3);
        scheduler.setThreadNamePrefix("EP-SCHEDULER-");
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        scheduler.setAwaitTerminationSeconds(1);
        scheduler.initialize();
        scheduler.schedule(getRunnable(),getTrigger());
    }


    /**
     * runnable
     *
     * @return
     */
    private Runnable getRunnable() {
        return this::runner;
    }


    /**
     * runner interface
     */
    public abstract void runner();


    /**
     * trigger interface
     * @return
     */
    public abstract Trigger getTrigger();
}
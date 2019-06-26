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
import com.klaytn.enterpriseproxy.queue.local.Local;


public class QueueTest {
    public static void main(String[] args) {
        LocalQueue localQueue = new LocalQueue("TESTKEY");

        Thread producer = new Thread(new Producer(localQueue));
        producer.start();

        Thread consumer = new Thread(new Consumer(localQueue));
        consumer.start();

        Thread insert = new Thread(new Insert(localQueue));
        insert.start();

        Thread insert2 = new Thread(new Insert2(localQueue));
        insert2.start();
    }
}


class Producer implements Runnable {
    private LocalQueue localQueue;
    Producer(LocalQueue localQueue) {
        this.localQueue = localQueue;
    }

    public void run() {
        try {
            int i = 0;
            while(true) {
                int addNum = i + 1;
                localQueue.add("StringI" + addNum);
                System.out.println("Added : String i " + addNum);
                Thread.currentThread().sleep(20);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class Consumer implements Runnable {
    private LocalQueue localQueue;
    Consumer(LocalQueue localQueue) {
        this.localQueue = localQueue;
    }

    public void run() {
        String str;
        try {
            int x = 0;
            while(true) {
                int addNum = x + 1;
                localQueue.add("StringX" + addNum);
                System.out.println("Added : String x " + addNum);
                Thread.sleep(30);
                x++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class Consumer2 implements Runnable {
    private Local<String> local;
    Consumer2(Local local) {
        this.local = local;
    }

    public void run() {
        String str;
        try {
            int z = 0;
            while(true) {
                int addNum = z + 1;
                local.add("StringZ" + addNum);
                System.out.println("Added : String z " + addNum);
                Thread.sleep(40);
                z++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class Consumer3 implements Runnable {
    private Local<String> local;
    Consumer3(Local local) {
        this.local = local;
    }

    public void run() {
        String str;
        try {
            int d = 0;
            while(true) {
                int addNum = d + 1;
                local.add("StringD" + addNum);
                System.out.println("Added : String d " + addNum);
                Thread.sleep(50);
                d++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class Insert implements Runnable {
    private LocalQueue localQueue;
    Insert(LocalQueue localQueue) {
        this.localQueue = localQueue;
    }

    public void run() {
        try {
            while(true) {
                long queueSize = localQueue.getQueueSize();
                if (queueSize == 0) {
                    continue;
                }

                if (queueSize > 100000) {
                    System.out.println("INSERT QUEUE SIZE : " + queueSize);
                    new Read(localQueue);
                }

                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class Insert2 implements Runnable {
    private LocalQueue localQueue;
    Insert2(LocalQueue localQueue) {
        this.localQueue = localQueue;
    }

    public void run() {
        try {
            while(true) {
                long queueSize = localQueue.getQueueSize();
                if (queueSize == 0) {
                    continue;
                }

                if (queueSize > 2000000) {
                    System.out.println("INSERT 2 QUEUE SIZE : " + queueSize);
                    new Read(localQueue);
                }

                Thread.sleep(300);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class Read {
    Read(LocalQueue local) {
        System.out.println("QUEUE LIST : " + local.getQueueList());
        local.clearQueue();
    }
}
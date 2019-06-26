# Enterprise Proxy (Common Module)
These are modules managing common libraries used in EP, such as Util and Scheduler.
Please refer to [pom.xml](https://github.com/ground-x/enterprise_proxy/blob/master/ep-common/pom.xml) for the common library contents. We recommend to add libraries in the corresponding modules, if you want.

***It's not mandatory to use the UTIL provided by the module.***



### UTILS

- ArrayUtil
- CharUtil
- ClassUtil
- CollectionUtil
- DateUtil
- JsonUtil
- MapUtil
- ObjectUtil
- StringUtil

---

### SCHEDULER
- ScheulerAbstract
  - This is developed based on ThreadPoolTaskScheduler. ([Class ThreadPoolTaskScheduler](https://docs.spring.io/spring-framework/docs/5.1.7.RELEASE/javadoc-api/org/springframework/scheduling/concurrent/ThreadPoolTaskScheduler.html?is-external=true))
  - ThreadPoolTaskScheduler options can be modified according to your dev or prod environments.
  ```java
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
  ```
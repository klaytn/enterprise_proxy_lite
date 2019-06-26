# Enterprise Proxy (Common Module)
EP에서 사용하는 Util 및 Scheduler와 같은 공통 라이브러리를 관리하는 모듈입니다.
공통 라이브러리 내용은 [pom.xml](https://github.com/ground-x/enterprise_proxy_base/blob/master/ep-common/pom.xml)을 참고 하시길 바라며 추가하고 싶은 라이브러리는 해당 모듈에서 추가하시기를 권장드립니다.

***해당 모듈에서 제공하는 UTIL을 꼭 사용하실 필요는 없습니다.***



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
  - ThreadPoolTaskScheduler 기반으로 개발 되었습니다. ([Class ThreadPoolTaskScheduler](https://docs.spring.io/spring-framework/docs/5.1.7.RELEASE/javadoc-api/org/springframework/scheduling/concurrent/ThreadPoolTaskScheduler.html?is-external=true))
  - ThreadPoolTaskScheduler 옵션 정보는 개발 및 배포 환경에 맞추어 직접 수정하시면 됩니다.
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
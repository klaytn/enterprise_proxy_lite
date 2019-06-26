# Enterprise Proxy (Queue Module)
EP에서 사용하는 Queue 모듈 입니다. 현재로서는 LocalQueue형태로 사용하고 있으나 요청에 따라 확장할 예정에 있습니다.



### SPEC
- Map 으로 제공되어 key 기준으로 value에 List형태로 queue를 운영할수 있습니다.
- Thread Safe를 위해 ConcurrentHashMap으로 제공됩니다.
  - [Class ConcurrentHashMap<K,V>](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentHashMap.html)
- value 역시 thread safe를 위해 CopyOnWriteArrayList로 제공됩니다. 
  - [Class CopyOnWriteArrayList<E>](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CopyOnWriteArrayList.html)

---

### LOCAL QUEUE INTERFACE
```java
public interface Local<T> {
    static Local build(String queueKey) {
        return new LocalQueue<>(queueKey);
    }

    void add(T value);
    long getQueueSize();
    List<T> getQueueList();
    void clearQueue();
}
```
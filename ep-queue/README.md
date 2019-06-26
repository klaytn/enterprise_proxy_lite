# Enterprise Proxy (Queue Module)
This is the Queue module used in EP. It is used as a form of LocalQueue for now, but we're planning to expand it upon request.



### SPECIFICATIONS
- It is provided as a type of Map so that you can manage queues as Lists of values from a key.
- It is provided as ConcurrentHashMap for Thread Safe.
  - [Class ConcurrentHashMap<K,V>](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentHashMap.html)
- Values are provided as CopyOnWriteArrayList for Thread Safe, as well.
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
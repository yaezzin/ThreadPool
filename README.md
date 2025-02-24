# ThreadPool

![image](https://github.com/user-attachments/assets/2710bab7-b65d-4352-af5f-c78cc8302bb0)

* ✅ Thread, Executor, ExecutorService, ThreadPool에 대한 개념을 학습하고, 궁금증이 생긴 것을 해결하는 저장소 

## 1. Thread

```java
Runnable task = new Runnable() {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
};

Thread t = new Thread(task);
t.start();
```

- Thread는 Java의 `java.lang.Thread` 클래스로, 병렬로 코드를 실행하기 위한 구체적인 방법을 제시
- Thread는 생성자에서 Runnable을 받아 실행하며, 한 번에 하나의 Runnable만 수행 가능
    - 내부에 run 메서드를 구현하고, ``start()``를 호출해야함
    - start() 메서드 호출 시 새로운 콜스택이 생성되며 run() 스레드를 올림. 반면 run() 메서드는 새로운 콜스택이 새로 생성되지 않기 때문에 main 스레드 위에 run() 스레드가 올라감
- Thread를 사용할 때는 개발자가 스레드를 직접 생성하고 시작해야함

**✅ 주요 메서드**

- **sleep**
    - 현재 스레드를 멈추기
    - 자원을 놓아주지는 않고, 제어권을 넘겨주므로 데드락이 발생 가능
- **interupt**
    - 다른 쓰레드를 깨워 InterruptedException을 발생
- **join**
    - 다른 쓰레드의 작업이 끝날 때까지 기다리게 함
    - 쓰레드의 순서를 제어할 때 사용 가능

**✅ Runnable**

```java
public interface Runnable {
    void run();
}
```

- 스레드가 실행할 작업을 정의하는 인터페이스
- Runnable은 Thread, Executor에 의해 호출
- Runnable은 익명 객체, 람다로 사용할 수 있으나, Thread는 별도의 클래스를 만들어야 함 → Thread를 상속 받는 경우 다른 클래스를 상속받을 수 없음(다중 상속X)

**😡 Thread의 문제점**

- Thread 객체는 한 번 실행되면 다시 재사용할 수 없으며, 새로운 작업을 수행하기 위해서는 새로운 스레드를 만들어야 함 → 매번 `new Thread()`를 호출하면 불필요한 리소스 낭비 발생
- 스레드 개수를 제한하지 않으면 `OutOfMemoryError`가 발생할 수 있음
- 스레드의 작업이 끝난 후의 결과값을 반환 받는 것이 불가능


## 2. Executor Interface

```java
public interface Executor {
    void execute(Runnable command);
}
```

- 동시에 여러 요청이 들어올 때마다 스레드를 생성하는 것을 비효율적이므로,  쓰레드를 미리 만들어 놓고 사용하는 쓰레드풀(Thread Pool) 등장
- Executor 인터페이스는 쓰레드풀 구현을 위한 인터페이스
- **등록된 작업(Runnable)을 실행하는 메서드만 존재**

## 3. ExecutorService

![image](https://github.com/user-attachments/assets/48008ace-a1d0-4311-a662-89272ee2783f)

- ExecutorService는 Executor를 상속받아 **작업 등록과 실행**을 책임지는 인터페이스
    - Executor는 단순히 작업을 실행하는 기능만 존재 → 작업 완료 여부 확인 불가능, 스레드 풀 종료 기능X
- ExecutorService는 종료를 관리하는 메소드와 비동기 작업의 진행 상태를 추적할 수 있는 Future를 생성하는 메소드를 제공

✅ **ExecutorService의 메소드**

1 ) `shutdown`

- **현재 실행 중인 작업은 완료**되고 **새로운 작업은 더 이상 받아들이지 않음**
- shutdown이 호출되기 전까지 계속해서 다음 작업을 기다리기 때문에 작업이 완료되었다면 shutdown을 호출해줘야 함
- shutdown 호출 후 새로운 작업을 execute하면 RejectedExecutionException 발생

2 ) `shutdownNow`

- **현재 실행 중인 모든 작업을 중단하려 시도하고, 대기 중인 작업들의 목록을 반환**
- 실행 중인 작업들이 종료될 때까지 **기다리지 않음**

3 ) `isShutdown`

- executor가 종료되었으면 true를 반환

4 ) `isTerminated`

- 모든 작업이 정상적으로 종료되었는지 확인
- 반드시 shutdown, shutdownNow을 호출해야 true를 반환

5 ) `awaitTermination`

```java
boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException;
```

- ExecutorService가 완전히 종료될 떄까지 기다리는 기능을 제공
- **timeout** → 최대 대기시간
- **unit** → timeout에 대한 시간단위를 지정

6 ) `submit`

- Runnable 또는 Callable을 추가하고, Future를 반환
- 작업이 성공적으로 수행되면 Futurue의 get메소드를 통해 결과값을 얻을 수 있음

7 ) `invokeAll`

```java
<T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) // 1
<T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) // 2
```

- 여러 개의 작업을 동시에 추가하고, 모든 작업이 완료되어야 함수가 종료
- 여러 개의 작업을 동시에 추가하고, timeout을 받아 시간안에 끝나지 않으면 종료
- **Exception**
    - InterruptedException
    - CancellationException
    - NullPointerException

8 ) `invokeAny`

- 가장 빨리 완료된 작업때까지 대기하는 방식

## 4. ScheduledExcutorService

```java
public interface ScheduledExecutorService extends ExecutorService
```

- 일정 시간 간격으로 작업을 예약하여 실행할 수 있도록 도와주는 인터페이스
- ExecutorService 인터페이스를 확장하며, 스레드 풀을 기반으로 동작

**✅ 주요 메서드**

- **schedule(Runnable command, long delay, TimeUnit unit)**
    - 주어진 delay 시간(단위는 TimeUnit으로 지정)에 따라 작업을 한 번만 실행
    - Ex. 5초 후에 작업을 실행하고 싶다면 delay를 5로, unit을 TimeUnit.SECONDS로 설정
- **schedule(Callable<V> callable, long delay, TimeUnit unit)**
    - 주어진 delay 시간 후에 Callable 작업을 한 번 실행합니다. Callable은 Runnable과 비슷하지만 반환값이 존재
- **scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit)**
    - 첫 번째 작업을 initialDelay 시간 후에 시작하고, 이후에는 period 간격으로 반복해서 실행
    - 주기적인 작업을 설정할 때 사용 → ex. 1초마다 작업을 반복하고 싶다면 period를 1로 설정
    - 주기적으로 실행되기 때문에, 이전 작업이 끝날 때까지 기다리지 않음
- **scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit)**
    - 첫 번째 작업을 initialDelay 시간 후에 시작하고, 이후 작업은 각 작업이 끝난 후 delay만큼의 간격을 두고 실행
    - scheduleAtFixedRate와 비슷하지만, 이 방식은 이전 작업이 끝난 후 다음 작업이 시작→ 즉, 작업 간에 고정된 지연을 둘 때 사용

## 5. Executors

Executors 클래스는 다양한 Executor 구현체를 쉽게 생성할 수 있는 팩토리 메서드를 제공

**1 ) newFixedThreadPool(int nThreads)**

- **고정된 수의 스레드**를 재사용하는 스레드 풀을 생성
- 지정된 nThreads 개수만큼 스레드가 동시에 작업을 처리

**2 ) newCachedThreadPool**

- 풀에 스레드가 없으면 새로운 스레드를 생성하고, 기존 스레드가 생성 가능하다면 그 스레드를 생성함

**3 ) newSingleThreadExecutor**

- 단일 스레드로 작업을 **순차적**으로 처리하는 Executor를 생성

## 6. ThreadPoolExecutor

```java
public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());
}
```

Executors의 팩토리 메서드 중 newFixedThreadPool을 까보면 `ThreadPoolExecutor` 클래스의 생성자를 호출하고 있다.

```java
public ThreadPoolExecutor(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue) {
}
```

- **corePoolSize**
    - 스레드 풀에 유지시킬 스레드의 개수
- **maximumPoolSize**
    - 스레드 풀에 최대로 유지될 수 있는 스레드의 개수
    - workqueue가 가득 찼을 때 maximumPoolSize만큼 스레드가 증가
- **keepAliveTime**
    - workqueue가 가득차면 maximumPoolSize까지 스레드 개수가 증가하면 증가된 스레드를 유지시킬 시간을 정의
- **workQueue**
    - corePoolSize만큼 꽉 찼을 경우 작업들을 담아두는 큐
- **TimeUnit**

```
💥
스레드 풀에 corePoolSize만큼 스레드를 생성해놓는 것이 아니라, 실제로는 스레드 작업이 요청될 때 스레드가 스레드풀에 생성된다.
그러다 스레드풀이 corePoolSize만큼 스레드를 생성하게되면 추가적인 스레드가 생성되지 않는다.
corePoolSize만큼 스레드풀을 채우고 싶다면 ThreadPoolTaskExecutor의 setPrestartAllCoreThreads를 true로 설정하면 된다.
```

```
💥
corePoolSize를 초과하는 요청이 들어오더라도 maximumPoolSize만큼 증가시키지는 않는다.
즉 대기 큐의 크기가 꽉찼을 경우에 maxPoolSize까지 스레드 풀이 증가하게 된다.
하지만 queueCapacticy가 Integer.MAX_VALUE로 설정되어 있기 때문에
(설정을 변경하지 않는다면) 큐의 크기가 꽉찰 일이 없어 maxPoolSize만큼 증가하는 상황이 발생하지 않는다.
```
![image](https://github.com/user-attachments/assets/fa2b9cbb-f9b2-4191-a7e8-69776b42b0c9)

- corePoolSize를 초과하는 요청이 증가 → 대기 큐가 꽉참 → 그래도 여전히 queueCapacity가 MAX_VALUE이기 때문에 maxPoolSize만큼 증가하지 않음

## 참고자료

* https://mangkyu.tistory.com/259
* https://dkswnkk.tistory.com/745


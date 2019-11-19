package io.github.pleuvoir.more.guava.eventbus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.MoreExecutors;
import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class EventBusTest {


    public static void main(String[] args) {

        EventBus eventBus = new EventBus("default-event-bus");

        Object objEvent = new Object() {
            @Subscribe
            public void test(String event) {
                System.out.println("你好，event=" + event);
            }
        };

        //注册两次也只会受到一次
        eventBus.register(objEvent);
        eventBus.register(objEvent);

        eventBus.post("我是消息");
        eventBus.post("我是消息2");

        /**
         * 你好，event=我是消息
         * 你好，event=我是消息2
         */

        EventBus manBus = new EventBus("manBus");
        ManListener manListener = new ManListener();
        HumanListener humanListener = new HumanListener();
        manBus.register(manListener);
        manBus.register(humanListener);

        Man man = new Man("fw");

        /**
         * 如果有继承关系 则父类的监听器也会收到请求 建议不要继承
         * ManListener =--=-={"name":"fw"}
         * HumanListener =--=-={"name":"fw"}
         */


        manBus.post(man);


        manBus.post(man.getName());

        /**
         * 会自动识别类型
         * ManListener =--=-= name = fw
         */


        /**
         * 当发布的对象找不到时可以注册一个 兜底的处理器  （一般没啥用）
         */
        manBus.register(new DeadEventListener());
        manBus.post(new Woman());   //Woman对象没有对应的处理器



        //异常情况
        EventBus exceptionBus = new EventBus("异常处理器");
        exceptionBus.register(new ExceptionListener());

        exceptionBus.post(new Exception());

        System.out.println("我会继续输出");

        final EventBus betterExceptionBus = new EventBus((exception,context) -> {
            System.out.println(context.getEvent());//Exception event
            System.out.println(context.getEventBus());//defalut
            System.out.println(context.getSubscriber());//ExceptionListener
            System.out.println(context.getSubscriberMethod());//m3
        });

        betterExceptionBus.register(new ExceptionListener());

        betterExceptionBus.post(new Exception());

        System.out.println("呵呵呵呵betterExceptionBus");
        /**
         * io.github.pleuvoir.more.guava.eventbus.Exception@51565ec2
         * EventBus{default}
         * io.github.pleuvoir.more.guava.eventbus.ExceptionListener@675d3402
         * public void io.github.pleuvoir.more.guava.eventbus.ExceptionListener.handler(io.github.pleuvoir.more.guava.eventbus.Exception)
         */


        // 测试异步

        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        AsyncEventBus asyncEventBus = new AsyncEventBus("异步任务处理器", threadPool);
        asyncEventBus.register(new ExceptionListener());
        asyncEventBus.post(new Exception());


        System.out.println("呵呵呵呵AsyncEventBus");
        MoreExecutors.shutdownAndAwaitTermination(threadPool, 8, TimeUnit.SECONDS);

    }
}

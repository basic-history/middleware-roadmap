package io.github.pleuvoir.more.guava.eventbus;

import com.google.common.eventbus.Subscribe;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class ExceptionListener {


    @Subscribe
    public void handler(Exception event){
        throw  new RuntimeException("我是异常");
    }
}

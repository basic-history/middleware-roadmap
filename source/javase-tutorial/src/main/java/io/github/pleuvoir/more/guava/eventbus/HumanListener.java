package io.github.pleuvoir.more.guava.eventbus;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.Subscribe;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class HumanListener {

    @Subscribe
    public void handler(Human event) {
        System.out.println("HumanListener =--=-=" + JSON.toJSONString(event));
    }
}

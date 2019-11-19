package io.github.pleuvoir.more.guava.eventbus;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.Subscribe;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class ManListener {

    @Subscribe
    public void handler(Man event) {
        System.out.println("ManListener =--=-=" + JSON.toJSONString(event));
    }

    @Subscribe
    public void handler(String name) {
        System.out.println("ManListener =--=-= name = " + name);
    }
}

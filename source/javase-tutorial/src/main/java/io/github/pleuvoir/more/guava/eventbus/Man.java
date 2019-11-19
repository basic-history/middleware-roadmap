package io.github.pleuvoir.more.guava.eventbus;

/**
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
public class Man extends Human{

    private String manSelef;

    public Man(String name) {
        super(name);
    }

    public String getManSelef() {
        return manSelef;
    }

    public void setManSelef(String manSelef) {
        this.manSelef = manSelef;
    }
}

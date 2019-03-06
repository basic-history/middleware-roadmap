package io.github.pleuvoir.slave.testing.spi;

public class SlaveDynamicConfig implements DynamicConfig {

	public SlaveDynamicConfig() {
		System.out.println("SlaveDynamicConfig init");
	}

	@Override
	public String getString(String key) {
		return "ok";
	}

}

package io.github.pleuvoir.slave.testing.spi;

public class SlaveDynamicConfig_2 implements DynamicConfig {

	@SuppressWarnings("unused")
	private String name;

	public SlaveDynamicConfig_2(String name) {
		super();
		this.name = name;
	}

	public SlaveDynamicConfig_2() {
		System.out.println("SlaveDynamicConfig_2 init");
	}

	@Override
	public String getString(String key) {
		return "ok";
	}

}

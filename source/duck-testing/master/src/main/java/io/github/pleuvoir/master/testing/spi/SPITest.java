package io.github.pleuvoir.master.testing.spi;

import java.util.ServiceLoader;

import io.github.pleuvoir.slave.testing.spi.DynamicConfig;

public class SPITest {

	public static void main(String[] args) {
		ServiceLoader<DynamicConfig> factories = ServiceLoader.load(DynamicConfig.class);
		for (DynamicConfig dynamicConfig : factories) {
			System.out.println(dynamicConfig);
		}
	}
}

package io.github.pleuvoir.master.testing.javassist;

public interface Compiler {

	Class<?> compile(String code, ClassLoader classLoader);
}

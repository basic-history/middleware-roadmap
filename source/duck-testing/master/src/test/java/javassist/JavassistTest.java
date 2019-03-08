package javassist;

import io.github.pleuvoir.master.testing.javassist.JavassistCompiler;

public class JavassistTest {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		
		JavassistCompiler compiler = new JavassistCompiler();
		String code = buildCode();
		System.out.println(code);
		Class<?> echoServiceClazz = compiler.compile(code, JavassistTest.class.getClassLoader());
		
		EchoService echoService = (EchoService) echoServiceClazz.newInstance();
		echoService.echo();
	}
	
	
	private static String buildCode() {
		StringBuffer bf = new StringBuffer("package javassist;\n");
		bf.append("public class EchoService {\n");
		bf.append("	public void echo() {\n");
		bf.append("		System.out.println(\"hello world\");\n");
		bf.append("	}\n");
		bf.append("}\n");
		return bf.toString();
	}
}

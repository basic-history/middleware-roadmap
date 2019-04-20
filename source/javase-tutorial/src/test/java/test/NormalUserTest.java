package test;

public class NormalUserTest extends NomarlAbstractUser {

	public NormalUserTest d() {
		return this;
	}

	public static void main(String[] args) {
		  new NormalUserTest().a().b().c();//不行了因为方法返回的是NomarlAbstractUser
	}
}

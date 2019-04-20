package test;

public class UserTest extends AbstrtactUser<UserTest> {

	public UserTest d() {
		return this;
	}

	public static void main(String[] args) {
		new UserTest().a().b().c().d();
	}
}

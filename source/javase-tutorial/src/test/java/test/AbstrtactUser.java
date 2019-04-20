package test;

public abstract class AbstrtactUser<B extends AbstrtactUser<B>> {

	public B a() {
		return self();
	}

	public B b() {
		return self();
	}

	public B c() {
		return self();
	}

	@SuppressWarnings("unchecked")
	private B self() {
		return (B) this;
	}
}

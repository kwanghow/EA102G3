package idv.david.methodenhance;

public interface A {
	/*public*/  default void m() {
		System.out.println(A.class.getSimpleName());
	}
}

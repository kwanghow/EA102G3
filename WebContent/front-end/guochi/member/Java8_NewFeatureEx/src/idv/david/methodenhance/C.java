package idv.david.methodenhance;

public interface C extends A {
    @Override
    default void m(){
        System.out.println(C.class.getSimpleName());
    }
}

package idv.david.methodenhance;

public class D implements B, C{
    // must implement m() here
    
    @Override
    public void m(){
        System.out.println(D.class.getSimpleName());
    }
    
}

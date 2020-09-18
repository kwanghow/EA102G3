package idv.david.lambda;

import java.util.List;
import java.util.function.Predicate;

public class PenHandler05Test {

	public static void main(String[] args) {
		List<Pen> penList = Pen.initPens();
		PenHandler05 ph5 = new PenHandler05();
		
		Predicate<Pen> allExpensivePens = 
				p -> p.getPrice() >= 1000;
		
		Predicate<Pen> allInSafeStockPens =
				p -> p.getStock() >= 50 && p.getStock() <= 100 && p.getPrice() <= 1000;
		
		Predicate<Pen> allCanErasedPens = 
				p -> p.isCanErase();
		

		System.out.println("\n==== Pen Test 05");
		System.out.println("\n=== Expensive pens");
		ph5.expensivePens(penList, allExpensivePens);

		System.out.println("\n=== In safe stock pens");
		ph5.safeStockPens(penList, allInSafeStockPens);

		System.out.println("\n=== Erased pens");
		ph5.erasedPens(penList, allCanErasedPens);
	}

}

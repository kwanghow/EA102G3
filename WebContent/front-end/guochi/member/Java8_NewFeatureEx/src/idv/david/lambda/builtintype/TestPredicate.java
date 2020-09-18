package idv.david.lambda.builtintype;

import java.util.List;
import java.util.function.Predicate;

public class TestPredicate {

	public static void main(String[] args) {
		List<Pen> penList = Pen.initPens();
		
		Predicate<Pen> allExpensivePens = 
				p -> p.getPrice() >= 1000; 
				
		penList.stream()
			.filter(allExpensivePens)
			.forEach(Pen::printDetails);
		
		System.out.println("\n==== comparison");
		
		for (Pen p : penList) {
			if (allExpensivePens.test(p)) {
				p.printDetails();
			}
		}
	}

}

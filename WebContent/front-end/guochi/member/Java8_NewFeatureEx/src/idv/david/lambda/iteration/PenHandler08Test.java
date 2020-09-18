package idv.david.lambda.iteration;

import java.util.List;
import java.util.function.Predicate;

public class PenHandler08Test {

	public static void main(String[] args) {
		
		List<Pen> penList = Pen.initPens();
		
		penList.stream()
			.filter(p -> p.getPrice() >= 1000)
			.forEach(p -> p.printDetails());
		
		
		Predicate<Pen> allExpensivePens = 
				p -> p.getPrice() >= 1000; 
		
		penList.stream()
			// you can pass a Predicate type argument in filter
			.filter(allExpensivePens)
			.forEach(p -> p.printDetails());
	}

}

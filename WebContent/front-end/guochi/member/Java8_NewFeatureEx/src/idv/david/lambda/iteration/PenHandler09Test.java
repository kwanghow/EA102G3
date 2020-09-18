package idv.david.lambda.iteration;

import java.util.List;

public class PenHandler09Test {

	public static void main(String[] args) {
		List<Pen> penList = Pen.initPens();
		
		penList.stream()
			.filter(p -> p.getPrice() >= 1000)
			.forEach(p -> p.printDetails());
		
		System.out.println("\n==== Use method reference as below\n");
		
		penList.stream()
			.filter(p -> p.getPrice() >= 1000)
			.forEach(Pen::printDetails);
		
		// 也可以
		penList.stream()
			.filter(p -> p.getPrice() >= 1000)
			.forEach(System.out::println);
	}

}

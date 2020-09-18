package idv.david.lambda.iteration;

import java.util.List;

public class PenHandler07Test {

	public static void main(String[] args) {
		List<Pen> penList = Pen.initPens();
		System.out.println("\n==== Pen Test 07");
		System.out.println("\n==== List all pens");
		
		// with Lambda
		penList.forEach(p -> p.printDetails());
		
	}

}

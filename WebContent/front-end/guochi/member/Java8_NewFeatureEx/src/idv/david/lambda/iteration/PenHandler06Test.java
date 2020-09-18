package idv.david.lambda.iteration;

import java.util.List;

public class PenHandler06Test {

	public static void main(String[] args) {
		List<Pen> penList = Pen.initPens();
		System.out.println("\n==== Pen Test 06");
		System.out.println("\n==== List all pens");
		
		// old styles
		for (Pen p : penList) {
			p.printDetails();
		}
		
	}

}

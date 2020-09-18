package idv.david.lambda;

import java.util.List;

public class PenHandler01Test {

	public static void main(String[] args) {
		List<Pen> penList = Pen.initPens();
		PenHandler01 ph1 = new PenHandler01();

		System.out.println("\n==== Pen Test 01");
		System.out.println("\n=== Expensive pens");
		ph1.expensivePens(penList);

		System.out.println("\n=== In safe stock pens");
		ph1.safeStockPens(penList);
		
		System.out.println("\n=== Erased pens");
		ph1.erasedPens(penList);
	}

}

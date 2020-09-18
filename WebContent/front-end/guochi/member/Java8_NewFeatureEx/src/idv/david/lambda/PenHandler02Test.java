package idv.david.lambda;

import java.util.List;

public class PenHandler02Test {

	public static void main(String[] args) {
		List<Pen> penList = Pen.initPens();
		PenHandler02 ph2 = new PenHandler02();

		System.out.println("\n==== Pen Test 02");
		System.out.println("\n=== Expensive pens");
		ph2.expensivePens(penList);

		System.out.println("\n=== In safe stock pens");
		ph2.safeStockPens(penList);

		System.out.println("\n=== Erased pens");
		ph2.erasedPens(penList);
	}
}

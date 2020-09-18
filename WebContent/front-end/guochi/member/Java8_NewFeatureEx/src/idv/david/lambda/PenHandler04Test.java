package idv.david.lambda;

import java.util.List;

public class PenHandler04Test {

	public static void main(String[] args) {
		List<Pen> penList = Pen.initPens();
		PenHandler04 ph4 = new PenHandler04();

		System.out.println("\n==== Pen Test 04");
		System.out.println("\n=== Expensive pens");
		ph4.expensivePens(penList, 
				(Pen pen) -> pen.getPrice() >= 1000);

		System.out.println("\n=== In safe stock pens");
		ph4.safeStockPens(penList,
				pen -> pen.getStock() >= 50 && pen.getStock() <= 100 && pen.getPrice() <= 1000);

		System.out.println("\n=== Erased pens");
		ph4.erasedPens(penList, 
				p -> p.isCanErase());
	}

}

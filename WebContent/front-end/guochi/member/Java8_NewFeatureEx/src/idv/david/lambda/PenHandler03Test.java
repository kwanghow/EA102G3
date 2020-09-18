package idv.david.lambda;

import java.util.List;
import java.util.function.Predicate;

public class PenHandler03Test {

	public static void main(String[] args) {
		List<Pen> penList = Pen.initPens();
		PenHandler03 ph3 = new PenHandler03();

		System.out.println("\n==== Pen Test 03");
		System.out.println("\n=== Expensive pens");
		ph3.expensivePens(penList, new Predicate<Pen>() {

			@Override
			public boolean test(Pen pen) {
				return pen.getPrice() >= 1000;
			}
		});

		System.out.println("\n=== In safe stock pens");
		ph3.safeStockPens(penList, new Predicate<Pen>() {

			@Override
			public boolean test(Pen pen) {
				return pen.getStock() >= 50 && pen.getStock() <= 100 && pen.getPrice() <= 1000;
			}
		});

		System.out.println("\n=== Erased pens");
		ph3.erasedPens(penList, new Predicate<Pen>() {

			@Override
			public boolean test(Pen pen) {
				return pen.isCanErase();
			}
		});
	}

}

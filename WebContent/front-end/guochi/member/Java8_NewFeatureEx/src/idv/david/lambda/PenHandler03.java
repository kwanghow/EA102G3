package idv.david.lambda;

import java.util.List;
import java.util.function.Predicate;

// Java 8 Functional Interface "Predicate" (預測)
public class PenHandler03 {

	public void expensivePens(List<Pen> penList, Predicate<Pen> pTest) {
		for (Pen p : penList) {
			if (pTest.test(p)) {
				System.out.println(p);
			}
		}
	}

	public void safeStockPens(List<Pen> penList, Predicate<Pen> pTest) {
		for (Pen p : penList) {
			if (pTest.test(p)) {
				System.out.println(p);
			}
		}
	}

	public void erasedPens(List<Pen> penList, Predicate<Pen> pTest) {
		for (Pen p : penList) {
			if (pTest.test(p)) {
				System.out.println(p);
			}
		}
	}

}

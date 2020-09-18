package idv.david.lambda;

import java.util.List;

public class PenHandler01 {
	
	public void expensivePens(List<Pen> penList) {
		for (Pen p : penList) {
			if (p.getPrice() >= 1000) {
				System.out.println(p);
			}
		}
	}

	public void safeStockPens(List<Pen> penList) {
		for (Pen p : penList) {
			if (p.getStock() >= 50 && p.getStock() <= 100 && p.getPrice() <= 1000) {
				System.out.println(p);
			}
		}
	}

	public void erasedPens(List<Pen> penList) {
		for (Pen p : penList) {
			if (p.isCanErase() == true) {
				System.out.println(p);
			}
		}
	}

}

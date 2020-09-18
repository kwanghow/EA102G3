package idv.david.lambda;

import java.util.List;

public class PenHandler02 {
	
	public void expensivePens(List<Pen> penList) {
		for (Pen p : penList) {
			if (isExpensivePen(p)) {
				System.out.println(p);
			}
		}
	}

	public void safeStockPens(List<Pen> penList) {
		for (Pen p : penList) {
			if (isInSafeStockPen(p)) {
				System.out.println(p);
			}
		}
	}

	public void erasedPens(List<Pen> penList) {
		for (Pen p : penList) {
			if (isErasedPen(p)) {
				System.out.println(p);
			}
		}
	}
	
	// 將判斷邏輯抽出成為方法，增加程式閱讀性
	public boolean isExpensivePen(Pen pen) {
		return pen.getPrice() >= 1000;
	}
	
	public boolean isInSafeStockPen(Pen pen) {
		return pen.getStock() >= 50 && pen.getStock() <= 100 && pen.getPrice() <= 1000;
	}
	
	public boolean isErasedPen(Pen pen) {
		return pen.isCanErase();
	}
}

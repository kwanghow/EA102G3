package idv.david.lambda.iteration;

import java.util.List;

public class PenHandler10Test {

	public static void main(String[] args) {
		List<Pen> penList = Pen.initPens();
		
		penList.stream()
			.filter(p -> p.getStock() >= 50 && p.getStock() <= 100 && p.getPrice() <= 1000)
			.forEach(Pen::printDetails);
		
		System.out.println();
		
		// 可考慮將複雜的邏輯轉換成以下method chaining方式，增加閱讀性
		penList.stream()
			.filter(p -> p.getStock() >= 50 && p.getStock() <= 100)
			.filter(p -> p.getPrice() <= 1000)
			.forEach(Pen::printDetails);
		
		
	}

}

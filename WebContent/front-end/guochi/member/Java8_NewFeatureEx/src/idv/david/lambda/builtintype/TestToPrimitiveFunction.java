package idv.david.lambda.builtintype;

import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;

public class TestToPrimitiveFunction {

	public static void main(String[] args) {
		List<Pen> penList = Pen.initPens();
		Pen pen = penList.get(0);
		
		ToDoubleFunction<Pen> discountFunction = 
				p -> p.getPrice() * 0.8;
				
		// 直接回傳基本型別資料，無須透過auto unboxing方式		
		double discount = discountFunction.applyAsDouble(pen);		
		System.out.println(discount);
		
		
		ToIntFunction<Pen> stockFunction = 
				p -> p.getStock();
		
		// 直接回傳基本型別資料，無須透過auto unboxing方式
		int stock = stockFunction.applyAsInt(pen);
		System.out.println(stock);
		
		
		
	}

}

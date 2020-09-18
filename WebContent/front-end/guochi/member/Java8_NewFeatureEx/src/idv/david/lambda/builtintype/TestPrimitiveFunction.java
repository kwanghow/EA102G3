package idv.david.lambda.builtintype;

import java.util.function.DoubleFunction;

public class TestPrimitiveFunction {

	public static void main(String[] args) {
		DoubleFunction<String> cal = 
				v -> String.valueOf(v * 3);
				
		String result = cal.apply(10.0);
		System.out.println(result);
		
		// 另有IntFunction, LongFunction等API可以使用，與上述相似
		
	}

}

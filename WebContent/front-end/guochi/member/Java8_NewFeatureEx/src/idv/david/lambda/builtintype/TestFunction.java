package idv.david.lambda.builtintype;

import java.util.List;
import java.util.function.Function;

public class TestFunction {

	public static void main(String[] args) {
		List<Pen> penList = Pen.initPens();
		Pen pen = penList.get(0);
		
		Function<Pen, Color> penColorFunction = 
				p -> p.getColor();
				
		System.out.println(penColorFunction.apply(pen));
		
	}

}

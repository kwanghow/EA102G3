package idv.david.lambda.builtintype;

import java.util.List;
import java.util.function.Consumer;

public class TestConsumer {

	public static void main(String[] args) {
		List<Pen> penList = Pen.initPens();
		Pen pen = penList.get(0);
		
		Consumer<Pen> penConsumer = 
				p -> System.out.println("Brand = " + p.getBrand() + " Price = " + p.getPrice());
				
		penList.stream()
			.forEach(penConsumer);
		
		System.out.println("\n==== Comparison");
		
		penConsumer.accept(pen);
	}

}

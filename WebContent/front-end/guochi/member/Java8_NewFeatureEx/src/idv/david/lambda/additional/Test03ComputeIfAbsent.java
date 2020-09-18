package idv.david.lambda.additional;

import java.util.HashMap;
import java.util.Map;

public class Test03ComputeIfAbsent {
	private static final String placeHolder = "TBD";

	public static void main(String[] args) {
		Map<String, String> stMap = new HashMap<>();

		stMap.put("WY", "Cheyenne");
		stMap.put("SD", "Pierre");
		stMap.put("CO", "Denver");

		stMap.computeIfAbsent("AL", v -> placeHolder);

		System.out.println("=== Print Map ===");
		stMap.forEach((k, v) -> System.out.println("Key: " + k + " " + "Value: " + v));
	}

}

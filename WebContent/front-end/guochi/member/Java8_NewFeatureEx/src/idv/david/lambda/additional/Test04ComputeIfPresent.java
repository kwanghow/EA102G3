package idv.david.lambda.additional;

import java.util.HashMap;
import java.util.Map;

public class Test04ComputeIfPresent {

	public static void main(String[] args) {
		Map<String, String> stMap = new HashMap<>();

		stMap.put("WY", "Cheyenne");
		stMap.put("SD", "Pierre");
		stMap.put("CO", "Denver");

		stMap.computeIfPresent("WY", (k, v) -> v + " (Verified " + k + ")");

		System.out.println("=== Print Map ===");
		stMap.forEach((k, v) -> System.out.println("Key: " + k + " " + "Value: " + v));
	}

}

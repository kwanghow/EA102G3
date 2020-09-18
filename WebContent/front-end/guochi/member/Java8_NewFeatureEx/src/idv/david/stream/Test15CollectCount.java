package idv.david.stream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test15CollectCount {

	public static void main(String[] args) {
		List<Employee> empList = Employee.createShortList();

		Map<String, Long> groupMap = new HashMap<>();

		// Collect CO Executives
		groupMap = empList.stream()
				.collect(Collectors.groupingBy(e -> e.getDept(), Collectors.counting()));

		System.out.println("\n== Employees by Dept ==");
		groupMap.forEach((k, v) -> System.out.println("Dept: " + k + " Count: " + v));
	}

}

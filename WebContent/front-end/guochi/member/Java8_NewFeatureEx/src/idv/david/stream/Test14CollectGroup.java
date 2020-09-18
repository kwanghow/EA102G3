package idv.david.stream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test14CollectGroup {

	public static void main(String[] args) {
		List<Employee> empList = Employee.createShortList();

		Map<String, List<Employee>> groupMap = new HashMap<>();

		// Collect CO Executives
		groupMap = empList.stream().collect(Collectors.groupingBy(Employee::getDept));

		System.out.println("\n== Employees by Dept ==");
		groupMap.forEach((k, v) -> {
			System.out.println("\nDept: " + k);
			v.forEach(Employee::printSummary);
		});
	}

}

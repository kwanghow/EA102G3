package idv.david.stream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test16CollectPartition {

	public static void main(String[] args) {
		List<Employee> empList = Employee.createShortList();

		Map<Boolean, List<Employee>> groupMap = new HashMap<>();

		// Collect CO Executives
		groupMap = empList.stream()
				.collect(Collectors.partitioningBy(e -> e.getRole().equals(Role.EXECUTIVE)));

		System.out.println("\n== Employees by Dept ==");
		groupMap.forEach((k, v) -> {
			System.out.println("\nGroup: " + k);
			v.forEach(Employee::printSummary);
		});
	}
}

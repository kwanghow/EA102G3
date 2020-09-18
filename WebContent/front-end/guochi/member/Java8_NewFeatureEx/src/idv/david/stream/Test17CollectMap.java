package idv.david.stream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test17CollectMap {

	public static void main(String[] args) {
		List<Employee> empList = Employee.createShortList();

		Map<String, String> groupMap = new HashMap<>();

		// Collect eMails and depts to a map
		groupMap = empList.stream()
				.collect(Collectors.toMap(Employee::getEmail, Employee::getDept));

		System.out.println("\n== Emails and depts ==");
		groupMap.forEach((k, v) -> {
			System.out.println("Email: " + k + " DeptName: " + v);
		});
		
	}

}

package idv.david.stream;

import java.util.List;
import java.util.Optional;

public class Test03FindFirst {

	public static void main(String[] args) {
		List<Employee> empList = Employee.createShortList();

		System.out.println("\n== First CO Bonus ==");
		Optional<Employee> result;

		result = empList.stream()
					.filter(e -> e.getRole().equals(Role.EXECUTIVE))
					.filter(e -> e.getState().equals("CO"))
					.findFirst();

		if (result.isPresent()) {
			result.get().print();
		}
	}
}

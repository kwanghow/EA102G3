package idv.david.stream;

import java.util.List;
import java.util.Optional;

public class Test05AnyMatch {

	public static void main(String[] args) {
		List<Employee> empList = Employee.createShortList();

		System.out.println("\n== First CO Bonus ==");
		Optional<Employee> result;

		if (empList.stream().anyMatch(e -> e.getState().equals("CO"))) {
			result = empList.stream()
						.peek(e -> System.out.println("Stream"))
						.filter(e -> e.getRole().equals(Role.EXECUTIVE))
						.filter(e -> e.getState().equals("CO"))
						.findFirst();

			if (result.isPresent()) {
				result.get().printSummary();
			}
		}

		System.out.println("\n== Any CA Bonus ==");
		if (empList.stream().anyMatch(e -> e.getState().equals("CA"))) {
			result = empList.stream()
					.peek(e -> System.out.println("Stream"))
					.filter(e -> e.getRole().equals(Role.EXECUTIVE))
					.filter(e -> e.getState().equals("CO"))
					.findFirst();

			if (result.isPresent()) {
				result.get().print();
			}
		}
	}

}

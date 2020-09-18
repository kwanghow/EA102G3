package idv.david.stream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Test11Collect {

	public static void main(String[] args) {
		List<Employee> empList = Employee.createShortList();

		List<Employee> newList = new ArrayList<>();

		// Collect CO Executives
		newList = empList.stream()
					.filter(e -> e.getRole().equals(Role.EXECUTIVE))
					.filter(e -> e.getState().equals("CO"))
					.sorted(Comparator.comparing(Employee::getSurName))
					.collect(Collectors.toList());

		System.out.println("\n== CO Bonus Details ==");

		newList.stream().forEach(Employee::printSummary);
	}

}

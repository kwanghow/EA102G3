package idv.david.stream;

import java.util.List;

public class Test09SortBonus {

	public static void main(String[] args) {
		List<Employee> empList = Employee.createShortList();

		System.out.println("\n== CO Bonus Details ==");

		empList.stream()
					.filter(e -> e.getRole().equals(Role.EXECUTIVE))
					.filter(e -> e.getState().equals("CO"))
					.mapToDouble(e -> e.getSalary() * Bonus.byRole(e.getRole()))
					.sorted()
					.forEach(d -> System.out.printf("Bonus paid: $%,6.2f %n", d));

		System.out.println("\n== CO Bonus Details ==");
		// what happened ?
		empList.stream()
					.filter(e -> e.getRole().equals(Role.EXECUTIVE))
					.filter(e -> e.getState().equals("CO"))
					.sorted()
					.forEach(Employee::printSummary);
	}

}

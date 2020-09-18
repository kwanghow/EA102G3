package idv.david.stream;

import java.util.List;

public class Test01Map {

	public static void main(String[] args) {
		List<Employee> empList = Employee.createShortList();

		System.out.println("\n== CO Bonuses ==");
		empList.stream()
			.filter(e -> e.getRole().equals(Role.EXECUTIVE))
			.filter(e -> e.getState().equals("CO"))
			.map(e -> e.getSalary() * Bonus.byRole(e.getRole()))
			.forEach(s -> System.out.printf("Bonus paid: $%,6.2f %n", s));

		System.out.println("\n== Eng Manager Bonus ==");
		
		empList.stream()
			.filter(e -> e.getRole().equals(Role.MANAGER))
			.filter(e -> e.getDept().equals("Eng"))
			.map(e -> e.getSalary() * Bonus.byRole(e.getRole()))
			.forEach(s -> System.out.printf("Bonus paid: $%,6.2f %n", s));

	}

}

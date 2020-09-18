package idv.david.parallel;

import java.util.List;

public class Test01OldStyleLoop {

	public static void main(String[] args) {

		List<Employee> empList = Employee.createShortList();

		double sum = 0;

		for (Employee e : empList) {
			if (e.getState().equals("CO") && e.getRole().equals(Role.EXECUTIVE)) {
				e.printSummary();
				sum += e.getSalary();
			}
		}

		System.out.printf("Total CO Executive Pay: $%,9.2f %n", sum);
	}
}

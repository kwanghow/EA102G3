package idv.david.stream;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Test06StreamData {

	public static void main(String[] args) {
		List<Employee> empList = Employee.createShortList();

		System.out.println("\n== Executive Count ==");
		long execCount = empList.stream()
				.filter(e -> e.getRole().equals(Role.EXECUTIVE))
				.count();

		System.out.println("Exec count: " + execCount);

		System.out.println("\n== Highest Paid Exec ==");
		Optional<Employee> highestExec = empList.stream()
				.filter(e -> e.getRole().equals(Role.EXECUTIVE))
				.max(Employee::sortBySalary);

		if (highestExec.isPresent()) {
			Employee temp = (Employee) highestExec.get();
			System.out.printf("Name: " + temp.getGivenName() + " " 
								+ temp.getSurName() 
								+ "   Salary: $%,6.2f %n ",
								temp.getSalary());
		}

		System.out.println("\n== Lowest Paid Staff ==");
		Optional<Employee> lowestStaff = empList.stream()
				.filter(e -> e.getRole().equals(Role.STAFF))
				.min(Comparator.comparingDouble(e -> e.getSalary()));

		if (lowestStaff.isPresent()) {
			Employee temp = (Employee) lowestStaff.get();
			System.out.printf("Name: " + temp.getGivenName() + " " 
								+ temp.getSurName() 
								+ "   Salary: $%,6.2f %n ",
								temp.getSalary());
		}
	}

}

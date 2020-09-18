package idv.david.stream;

import java.util.List;
import java.util.stream.Collectors;

public class Test12CollectMath {

	public static void main(String[] args) {
		List<Employee> empList = Employee.createShortList();
	       
        // Collect CO Executives
        double avgSalary = empList.stream()
        	.filter(e -> e.getRole().equals(Role.EXECUTIVE))
            .filter(e -> e.getState().equals("CO"))
            .collect(Collectors.averagingDouble(Employee::getSalary));        

        System.out.println("\n== CO Exec Avg Salary ==");
        System.out.printf("Average: $%,9.2f %n", avgSalary); 
	}

}

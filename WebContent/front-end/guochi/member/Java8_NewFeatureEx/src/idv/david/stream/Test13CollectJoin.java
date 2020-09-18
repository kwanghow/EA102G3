package idv.david.stream;

import java.util.List;
import java.util.stream.Collectors;

public class Test13CollectJoin {

	public static void main(String[] args) {
		List<Employee> empList = Employee.createShortList();
	       
        // Collect CO Executives
        String deptList = empList.stream()
        	.map(Employee::getDept)
            .distinct()
            .collect(Collectors.joining(", "));        

        System.out.println("\n== Dept List ==");
        System.out.println("Total: " + deptList); 
	}

}

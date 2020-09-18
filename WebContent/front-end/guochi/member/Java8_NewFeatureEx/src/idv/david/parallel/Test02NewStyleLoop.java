package idv.david.parallel;

import java.util.List;

public class Test02NewStyleLoop {
	
public static void main(String[] args) {
        
        List<Employee> empList = Employee.createShortList();
        
        double result = empList.stream()
            .filter(e -> e.getState().equals("CO"))
            .filter(e -> e.getRole().equals(Role.EXECUTIVE))
            .peek(e -> e.printSummary())
            .mapToDouble(e -> e.getSalary())
            .sum();
        
        System.out.printf("Total CO Executive Pay: $%,9.2f %n", result);               
    }
}

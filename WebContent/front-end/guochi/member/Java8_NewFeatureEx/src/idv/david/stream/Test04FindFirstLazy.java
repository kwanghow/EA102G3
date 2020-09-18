package idv.david.stream;

import java.util.List;
import java.util.Optional;

public class Test04FindFirstLazy {

	public static void main(String[] args) {
		List<Employee> empList = Employee.createShortList();

        System.out.println("\n== CO Bonuses ==");
        empList.stream()
            .peek(e -> System.out.println("Stream start"))
            .filter(e -> e.getRole().equals(Role.EXECUTIVE))
            .peek(e -> System.out.println("Executives"))
            .filter(e -> e.getState().equals("CO"))
            .peek(e -> System.out.println("CO Executives"))
            .map(e -> e.getSalary() * Bonus.byRole(e.getRole()))
            .forEach( s -> System.out.printf("  Bonus paid: $%,6.2f %n", s));
        
        // 惰性求值 Lazy Evaluation
        System.out.println("\n== First CO Bonus ==");
        Optional<Employee> result = empList.stream()
            .peek(e -> System.out.println("Stream start"))
            .filter(e -> e.getRole().equals(Role.EXECUTIVE))
            .peek(e -> System.out.println("Executives"))
            .filter(e -> e.getState().equals("CO"))
            .peek(e -> System.out.println("CO Executives"))                
            .findFirst();
        
        if (result.isPresent()){
            result.get().printSummary();
        }     
	}

}

package idv.david.stream;

import java.util.List;

public class Test07CalSum {

	public static void main(String[] args) {
		List<Employee> empList = Employee.createShortList();
        
	    System.out.println("\n== Total CO Bonus ==");
	    double result;
	        
	    result = empList.stream()
	    			.filter(e -> e.getRole().equals(Role.EXECUTIVE))
	    			.filter(e -> e.getState().equals("CO"))
	    			.mapToDouble(e -> e.getSalary() * Bonus.byRole(e.getRole()))
	    			.sum();
	        
	    System.out.printf("Total Bonuses paid: $%,6.2f %n", result);

	    System.out.println("\n== Total CO Bonus Details ==");
	        
	    result = empList.stream()
	    			.filter(e -> e.getRole().equals(Role.EXECUTIVE))
	    			.filter(e -> e.getState().equals("CO"))
	    			.peek(e -> System.out.print("Name: " + e.getGivenName() + " " + e.getSurName() + " "))
	    			.mapToDouble(e -> e.getSalary() * Bonus.byRole(e.getRole()))
	    			.peek(d -> System.out.printf("Bonus paid: $%,6.2f %n", d))
	    			.sum();
	        
	    System.out.printf("Total Bonuses paid: $%,6.2f %n", result);  
	}

}

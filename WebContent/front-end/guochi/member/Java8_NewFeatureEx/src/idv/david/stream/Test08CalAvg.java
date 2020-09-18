package idv.david.stream;

import java.util.List;
import java.util.OptionalDouble;

public class Test08CalAvg {

	public static void main(String[] args) {
		List<Employee> empList = Employee.createShortList();
        
	    System.out.println("\n== Average CO Bonus ==");
	    OptionalDouble result;
	        
	    result = empList.stream()
	    			.filter(e -> e.getRole().equals(Role.EXECUTIVE))
	    			.filter(e -> e.getState().equals("CO"))
	    			.mapToDouble(e -> e.getSalary() * Bonus.byRole(e.getRole()))
	    			.average();
	        
	    if (result.isPresent()){
	    	System.out.printf("Average Bonuses paid: $%,6.2f %n", result.getAsDouble());
	    }

	    System.out.println("\n== Average CO Bonus Details ==");
	        
	    result = empList.stream()
	    			.filter(e -> e.getRole().equals(Role.EXECUTIVE))
	    			.filter(e -> e.getState().equals("CO"))
	    			.peek(e -> System.out.print("Name: " + e.getGivenName() + " " + e.getSurName() + " "))
	    			.mapToDouble(e -> e.getSalary() * Bonus.byRole(e.getRole()))
	    			.peek(d -> System.out.printf("Bonus paid: $%,6.2f %n", d))
	    			.average();
	        
	      if (result.isPresent()){
	    	  System.out.printf("Average Bonuses paid: $%,6.2f %n", result.getAsDouble());
	      }    
	}

}

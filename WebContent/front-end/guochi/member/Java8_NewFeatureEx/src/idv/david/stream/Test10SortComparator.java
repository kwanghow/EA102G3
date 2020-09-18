package idv.david.stream;

import java.util.Comparator;
import java.util.List;

public class Test10SortComparator {

	public static void main(String[] args) {
		List<Employee> empList = Employee.createShortList();
	    
	    System.out.println("\n== CO Bonus Details Comparator ==");
	        
	    empList.stream()
	      			.filter(e -> e.getRole().equals(Role.EXECUTIVE))
	      			.filter(e -> e.getState().equals("CO"))
	      			.sorted(Comparator.comparing(Employee::getSurName))
	      			.forEach(Employee::printSummary);        

	    System.out.println("\n== CO Bonus Details Reversed ==");
	        
	    empList.stream()
	      			.filter(e -> e.getRole().equals(Role.EXECUTIVE))
	      			.filter(e -> e.getState().equals("CO"))
	      			.sorted(Comparator.comparing(Employee::getSurName).reversed())
	      			.forEach(Employee::printSummary);        
	        
	    System.out.println("\n== Two Level Sort, Dept then Surname ==");
	        
	    empList.stream()
	      			.sorted(Comparator.comparing(Employee::getDept).thenComparing(Employee::getSurName))
	      			.forEach(Employee::printSummary);            
	}

}

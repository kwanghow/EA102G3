package idv.david.lambda.additional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestCollectionsWithStream {
	
	public static void main(String[] args) {
		List<String> col1 = new ArrayList<String>(); // {a, b, c}
		col1.add("a");
		col1.add("b");
		col1.add("c");

		List<String> col2 = new ArrayList<String>(); // {b, c, d, e}
		col2.add("b");
		col2.add("c");
		col2.add("d");
		col2.add("e");
		
		// 差集
		List<String> differ = col1.stream()
				.filter(e -> !col2.contains(e))
				.collect(Collectors.toList());
		
		System.out.println(differ);
		
		// 交集
		List<String> inter = col1.stream()
			    .filter(col2::contains)
			    .collect(Collectors.toList());
		
		System.out.println(inter);
		
		// 聯集
		List<String> union = Stream.concat(col1.stream(), col2.stream())
				.distinct()
				.collect(Collectors.toList());
		
		System.out.println(union);
		
		
	}
	
}

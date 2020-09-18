package idv.david.lambda.iteration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;

class MyCompare {
	public int myCompare(String s1, String s2) {
		return s1.compareTo(s2) * -1;
	}
}


public class TestMethodReference {

	public static void main(String[] args) {
		// init data
		List<String> list = new ArrayList<>();
		list.add("hello");
		list.add("world");
		list.add("test");
		
		// (1) static method reference | ClassName::methodName
		list.forEach(System.out::println);
		
		// (2) instance method reference | Instance Reference::methodName
		MyCompare mc = new MyCompare();
//		Collections.sort(list, (s1, s2) -> mc.myCompare(s1, s2));
		Collections.sort(list, mc::myCompare);
		System.out.println(list);
		
		// (3) class type method reference | ClassName::methodName
		Collections.sort(list, String::compareTo);
		System.out.println(list);

		// (4) constructor method reference | ClassName::new
		Function<String, Book> bookMaker = Book::new;
		List<Book> bookList = new ArrayList<>();
		for (String str : list) {
			bookList.add(bookMaker.apply(str));
		}
		System.out.println(bookList);
		
		// (5) array constructor method reference | TypeName[]::new
		IntFunction<int[]> iFunction = int[]::new;
		int[] iArray = iFunction.apply(10);
		for (int i : iArray)
			System.out.print(i + " ");
		
	}

}

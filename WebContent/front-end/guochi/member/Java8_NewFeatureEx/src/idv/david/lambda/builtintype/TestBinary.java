package idv.david.lambda.builtintype;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public class TestBinary {
	private static final String SUFFIX = ".txt";
	
	public static void main(String[] args) {
		List<String> fileList = new ArrayList<>();
		fileList.add("hello.txt");
		fileList.add("Gakki.jpg");
		fileList.add("hello2.txt");
		fileList.add("secret.avi");
		
		BiPredicate<String, String> giveMeTxt =
				(t, r) -> t.endsWith(r);
				
		for (String str : fileList)
			System.out.println(giveMeTxt.test(str, SUFFIX));
		
	}

}

package idv.david.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TestOptional {

	public static void main(String[] args) {
		List<String> cityList = new ArrayList<>(Arrays.asList("Taipei", "Tokyo", "NewYork", "Seoul"));

		// ifPresent
		Optional<String> op1 = cityList.stream()
				.filter(name -> name.startsWith("T"))
				.findFirst();

		op1.ifPresent(System.out::println);

		// orElse
		Optional<String> op2 = cityList.stream()
				.filter(name -> name.startsWith("Z"))
				.findFirst();

		String result2 = op2.orElse("Milan");
		
		System.out.println(result2);
		
		// orElseGet
		Optional<String> op3 = cityList.stream()
				.filter(name -> name.startsWith("Z"))
				.findFirst();

		// 可以讓你有更複雜的邏輯處理寫在orElseGet裡
		String result3 = op3.orElseGet(() -> "Milan");
		
		System.out.println(result3);
		
		// orElseThrow
		Optional<String> op4 = cityList.stream()
				.filter(name -> name.startsWith("Z"))
				.findFirst();

		op4.orElseThrow(NoSuchElementException::new);
	}

}

package idv.david.lambda.iteration;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class IteratorForEachCompare {

	public static void main(String[] args) {
		long counter = 10000000L;
		List<Long> list = new ArrayList<>();
		for (long l = 0; l < counter; l++) {
			list.add(l);
		}
		
		// foreach (外部迭代)
		Instant start = Instant.now();
		for (Long l : list) {
			System.out.print("");
		}
		Instant end = Instant.now();
		System.out.println("===外部迭代耗費時間===" + Duration.between(start, end).toMillis());
		
		// forEach (Java 8 內部迭代)
		Instant start2 = Instant.now();
		list.stream()
			.forEach(l -> System.out.print(""));
		Instant end2 = Instant.now();
		System.out.println("===內部迭代耗費時間===" + Duration.between(start2, end2).toMillis());
		
	}

}

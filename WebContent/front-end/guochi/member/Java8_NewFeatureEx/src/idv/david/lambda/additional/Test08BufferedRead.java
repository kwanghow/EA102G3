package idv.david.lambda.additional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Test08BufferedRead {

	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new FileReader("tempest.txt"))) {

			br.lines()
				.forEach(line -> System.out.println("Line: " + line));

		} catch (IOException e) {
			System.out.println("Message: " + e.getMessage());
		}
	}

}

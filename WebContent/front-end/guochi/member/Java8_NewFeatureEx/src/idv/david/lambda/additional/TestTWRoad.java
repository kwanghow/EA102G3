package idv.david.lambda.additional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class TestTWRoad {

	public static void main(String[] args) throws IOException {
		TestTWRoad tw = new TestTWRoad();
		
		tw.unZipFile();
		Scanner sc = new Scanner(System.in);
		System.out.println("請輸入縣市： ");
		String city = sc.next();
		List<String> cities = tw.filterCity(city);
		System.out.println("請輸入行政區： ");
		String area = sc.next();
		tw.getRoads(cities, area);
		sc.close();
	}
	
	private void getRoads(List<String> cities, String area) {
		File dir = new File("MyZipFolder");
		cities.stream()
				.filter(s -> s.contains(area))
				.forEach(fileName -> {
					System.out.println("FileName = " + fileName);
					File file = new File(dir, fileName);
					try (BufferedReader br = new BufferedReader(new FileReader(file))) {
							br.lines()
								.flatMap(line -> Stream.of(line.split(",")))
								.forEach(System.out::println);
					} catch (IOException ie) {
						ie.printStackTrace();
					}
				});
	}
	
	private List<String> filterCity(String city) {
		File dir = new File("MyZipFolder");

		List<String> result = Stream.of(dir.list())
				.filter(s -> s.contains(city))
				.collect(Collectors.toList());
		
		result.stream()
			.forEach(System.out::println);
		
		return result;
	}

	private void unZipFile() throws IOException {
		File destDir = new File("MyZipFolder");
		if (!destDir.exists())
			destDir.mkdirs();
		Charset big5 = Charset.forName("Big5");
		File sourceFile = new File("2012_03_13_streetName_tw.zip");

		ZipInputStream zis = new ZipInputStream(new FileInputStream(sourceFile), big5);
		ZipEntry entry = null;
		while ((entry = zis.getNextEntry()) != null) {
			String entryName = entry.getName();
			if (entryName.endsWith(".txt")) {
				String fileName = entryName.substring(entryName.lastIndexOf("/") + 1);
				File destFile = new File(destDir, fileName);
				FileOutputStream fos = new FileOutputStream(destFile);
				int length = 0;
				byte[] buffer = new byte[64];
				while ((length = zis.read(buffer)) != -1) {
					fos.write(buffer, 0, length);
					fos.flush();
				}
				fos.close();
			}
		}
		zis.close();
		System.out.println("Done");
	}

}

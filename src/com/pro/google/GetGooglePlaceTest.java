package com.pro.google;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetGooglePlaceTest {
	private static final String GOOGLE_URL = 
			"https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
			+ "location=24.95375,121.22575&"
			+ "radius=500&"
			+ "types=food&"
			+ "name=¦Y¨ì¹¡&"
			+ "language=zh-TW&"
			+ "key=AIzaSyALEsEE6Qu3lLcOmmlgh0sWY4dbWAlW4_4";
			
	public static void main(String[] args) throws IOException {
		StringBuilder s1 = new StringBuilder();
		URL url = new URL(GOOGLE_URL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setUseCaches(false);
		
		int statusCode = con.getResponseCode();
		System.out.println(statusCode);
		
		InputStream is = con.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		
		String data;
		while ((data = br.readLine()) != null) {
			s1.append(data);
		}
		System.out.println(s1);
		br.close();
		isr.close();
		is.close();
		
//		pase JSON
		
		
	}
}

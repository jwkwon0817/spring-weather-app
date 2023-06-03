package com.codemos.springweatherapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Controller
public class IndexController {
	private static final String API_KEY = "3d06a52185b30480ad6f20a0a150d95a";
	
	@GetMapping("/api/weather")
	@ResponseBody
	public Map<String, String> returnText(@RequestParam("city") String city) throws IOException {
		final String apiLink = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY;
		
		try {
			URL url = new URL(apiLink);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			
			String inputLine;
			StringBuilder response = new StringBuilder();
			
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			return Map.of("data", response.toString());
		} catch (FileNotFoundException ex) {
			return Map.of("error", "Cannot find.");
		}
		
	}
}

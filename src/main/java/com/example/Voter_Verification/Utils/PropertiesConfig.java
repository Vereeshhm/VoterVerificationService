package com.example.Voter_Verification.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("file:src/main/resources/application.properties")
public class PropertiesConfig {

	
	@Value("${ApiUrl}")
	private String ApiUrl;

	@Value("${token}")
	private String token;
	
	public String getApiUrl() {
		return ApiUrl;
	}

	public void setApiUrl(String apiUrl) {
		ApiUrl = apiUrl;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}

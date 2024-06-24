package com.example.Voter_Verification.ServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.Voter_Verification.Entity.VoterRequest;
import com.example.Voter_Verification.Repository.ApiLogRepository;
import com.example.Voter_Verification.Service.VoterService;
import com.example.Voter_Verification.Utils.ApiLog;
import com.example.Voter_Verification.Utils.PropertiesConfig;
import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class VoterServiceImpl implements VoterService {

	@Autowired
	ApiLogRepository apiLogRepository;

	@Autowired
	PropertiesConfig config;

	@Autowired
	RestTemplate restTemplate;

	private static final Logger logger = LoggerFactory.getLogger(VoterServiceImpl.class);

	@Override
	public String getVerify(VoterRequest dto, HttpServletRequest request1, HttpServletResponse response) {

		String requestUrl = request1.getRequestURI().toString();

		dto.getEpicNumber();
		dto.getName();
		dto.getState();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", config.getToken());

		Gson gson = new Gson();

		String requestBodyJson = gson.toJson(dto);

		HttpEntity<String> entity = new HttpEntity(requestBodyJson, headers);

		ApiLog apiLog = new ApiLog();
		apiLog.setUrl(requestUrl);
		apiLog.setRequestBody(requestBodyJson);
		String response1 = null;
		try {
			response1 = restTemplate.postForObject(config.getApiUrl(), entity, String.class);
			apiLog.setResponseBody(response1);
			apiLog.setStatusCode(HttpStatus.OK.value());
			apiLog.setStatus("SUCCESS");
			logger.info("Responsepacket" + response1);
			return response1;

		}

		catch (HttpClientErrorException.TooManyRequests e) {

			apiLog.setStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
			apiLog.setStatus("FAILURE");
			response1 = e.getResponseBodyAsString();

			logger.info("Responsepacket" + response1);
			apiLog.setResponseBodyAsJson("API rate limit exceeded");
		} catch (HttpClientErrorException.Unauthorized e) {

			apiLog.setStatusCode(HttpStatus.UNAUTHORIZED.value());
			apiLog.setStatus("FAILURE");
			response1 = e.getResponseBodyAsString();
			logger.info("Responsepacket" + response1);
			apiLog.setResponseBodyAsJson("No API key found in request");

		}

		catch (HttpClientErrorException e) {

			apiLog.setStatusCode(e.getStatusCode().value());
			apiLog.setStatus("FAILURE");
			response1 = e.getResponseBodyAsString();
			logger.debug("Raw response: " + response1);
			logger.info("Responsepacket" + response1);
			apiLog.setResponseBody(response1);
		} catch (Exception e) {
			apiLog.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			apiLog.setStatus("ERROR");
			response1 = e.getMessage();

			apiLog.setResponseBody(response1);
		} finally {
			apiLogRepository.save(apiLog);
		}
		return response1;

	}

}

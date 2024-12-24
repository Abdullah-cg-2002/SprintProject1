package com.sprint.app.testingapi;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class ApiTest {
	
	private RestTemplate rt = new RestTemplate();
	
	//get method
	@Test
//	@Disabled
	public void testGetMethod() throws JSONException
	{
		
		
		String url = "http://localhost:9090/api/messages";
		String output = """
				{
    "status": "success"
}	
				""";
		
		ResponseEntity<String> resp = rt.getForEntity(url, String.class);
		assertTrue(resp.getStatusCode().is2xxSuccessful());
		JSONAssert.assertEquals(output, resp.getBody(), false);
	}
	
	//post put delete method
	@Test
	@Disabled
	public void testPostMethod() throws JSONException
	{
		String url = "http://localhost:9090/api/";
		String output = """
				{
    "status": "success",
    "message": ""
}
				""";
		ResponseEntity<String> resp = rt.getForEntity(url, String.class);
		assertTrue(resp.getStatusCode().is2xxSuccessful());
		JSONAssert.assertEquals(output, resp.getBody(), true);
	}
	
	

}

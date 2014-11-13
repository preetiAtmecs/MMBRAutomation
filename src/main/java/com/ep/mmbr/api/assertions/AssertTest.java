package com.ep.mmbr.api.assertions;


import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.skyscreamer.jsonassert.JSONAssert;
import org.testng.Assert;
import org.testng.Reporter;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
//import com.ep.reportengine.HtmlHelper;

public class AssertTest {

	JSONParser parser = new JSONParser();

	public void assertJsonString(String actual, String expected) throws JSONException
			 {

		
		JSONAssert.assertEquals(expected, actual, true);

	}

	public void assertStatusCode(Response response, int expectedStatusCode)
			throws NumberFormatException {
		System.out.println("\nResponse Status code: "+response.getStatusCode());
		

		int actualStatusCode = response.statusCode();

		Assert.assertEquals(actualStatusCode, expectedStatusCode,
				"Failed to match HTTP response status code");
	}
	
	
	public void assertHeader(Response response, JSONObject json)
			throws NumberFormatException, JSONException {
		Reporter.log("\nResponse Body: "+response.getStatusCode());
		int expectedStatusCode = Integer
				.parseInt(json.get("status").toString());

		int actualStatusCode = response.statusCode();

		Assert.assertEquals(actualStatusCode, expectedStatusCode,
				"Failed to match HTTP response status code");
	}

	

	public JsonPath getObject(Response response) {
		JsonPath object = new JsonPath(response.asString());

		return object;

	}
}

package com.ep.mmbr.api.testscripts.user;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ep.mmbr.api.testscripts.TestSuiteBase;
import com.ep.mmbr.api.utilities.TestHandler;
import com.ep.qa.automation.assertion.AssertionHandler;
import com.jayway.restassured.response.Response;

public class GetUserInformationFromToken extends TestSuiteBase {

	

	@Test
	public void testUserInformationFromToken() throws JSONException {
		TestHandler testHandler = new TestHandler();
		JSONObject getUserRequestData = testHandler.readFileData("user",
				"getUserInformationFromToken.json");

		Response getUserResponse = testHandler.sendRequestAndGetResponse(
				getUserRequestData, CONFIG.getProperty("SalesforceToken"));

		Assert.assertEquals(getUserResponse.getStatusCode(), Integer
				.parseInt(getUserRequestData.get("status").toString()));


		Assert.assertEquals(getUserResponse.getStatusCode(),
				Integer.parseInt(getUserRequestData.get("status").toString()));

		new AssertionHandler().assertJsonEquals(getUserResponse.asString(),
				getUserRequestData.get("responseBody").toString(),true);

	}

}

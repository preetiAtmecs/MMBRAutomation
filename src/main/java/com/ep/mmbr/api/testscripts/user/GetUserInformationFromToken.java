package com.ep.mmbr.api.testscripts.user;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
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

		Reporter.log("Sending get user information from token uri : "+getUserRequestData.get("uri"));
		Response getUserResponse = testHandler.sendRequestAndGetResponse(
				getUserRequestData, CONFIG.getProperty("SalesforceToken"));

		testHandler.verifyResponseCode(getUserResponse,getUserRequestData.get("status").toString());
		
		
		Reporter.log("Verifying user information from response body:");
		new AssertionHandler().assertJsonEquals(getUserResponse.asString(),
				getUserRequestData.get("responseBody").toString(),true);

	}

}

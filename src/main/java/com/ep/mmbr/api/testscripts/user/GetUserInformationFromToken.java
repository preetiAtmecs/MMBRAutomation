package com.ep.mmbr.api.testscripts.user;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.ep.mmbr.api.testscripts.TestSuiteBase;
import com.ep.mmbr.api.utilities.TestDataProvider;
import com.ep.mmbr.api.utilities.RequestDataBuilder;
import com.ep.qa.automation.assertion.AssertionHandler;
import com.jayway.restassured.response.Response;

/**
 * This script validate response code and user information from the given valid
 * token
 * 
 * @author pg092111
 * 
 */
public class GetUserInformationFromToken extends TestSuiteBase {

	/**
	 * Send request with valid salesforce token and verify the response code
	 * matches with 200 and the user information matches with the expected
	 * results
	 * 
	 * @author pg092111
	 * 
	 */
	@Test
	public void testUserInformationFromToken() throws Exception {
		RequestDataBuilder requestDataBuilder = new RequestDataBuilder();

		JSONObject getUserRequestData = new TestDataProvider().readFileData(
				"user", "getUserInformationFromToken.json");

		Reporter.log("Sending request to get user information from token");
		Response getUserResponse = requestDataBuilder.sendRequestAndGetResponse(
				getUserRequestData, CONFIG.getProperty("SalesforceToken"));

		Assert.assertTrue(requestDataBuilder.verifyResponseCode(getUserResponse,
				getUserRequestData.get("status").toString()));

		Reporter.log("<br>Verifying user information from response body json object:<br>Expected response body"
				+ getUserRequestData.get("responseBody").toString());
		
		
		AssertionHandler.assertJsonEquals(getUserResponse.asString(),
				getUserRequestData.get("responseBody").toString(), true);

	}

}

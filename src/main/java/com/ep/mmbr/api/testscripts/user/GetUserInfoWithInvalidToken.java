package com.ep.mmbr.api.testscripts.user;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.ep.mmbr.api.testscripts.TestSuiteBase;
import com.ep.mmbr.api.utilities.TestDataProvider;
import com.ep.mmbr.api.utilities.RequestHandler;
import com.jayway.restassured.response.Response;


/**
 * This script validate response code and user information from the given invalid
 * token
 * 
 * @author pg092111
 * 
 */
public class GetUserInfoWithInvalidToken extends TestSuiteBase {
	
	/**
	 * Send request with invalid salesforce token and verify the response code
	 * matches with 401 and the user information matches with the expected results
	 * 
	 * @author pg092111
	 * 
	 */
	@Test
	public void testUserInfoWithInvalidToken() {
		
		String invalidsalesforceToken = "djgeuuih24u38702dh28fu28fyh28f84fhcjsj";
		RequestHandler requestHandler = new RequestHandler();
		JSONObject getUserRequestData = new TestDataProvider().readFileData(
				"user", "getUserInfoWithInvalidToken.json");

		Reporter.log("Sending get user information with invalid token : "
				+ getUserRequestData.get("uri"));

		
		
		Response getUserResponse = requestHandler.sendRequestAndGetResponse(
				getUserRequestData, invalidsalesforceToken);

		Assert.assertTrue(requestHandler.verifyResponseCode(getUserResponse,
				getUserRequestData.get("status").toString()));

		Assert.assertEquals(getUserResponse.asString(),
				getUserRequestData.get("responseBody").toString());

	}

}

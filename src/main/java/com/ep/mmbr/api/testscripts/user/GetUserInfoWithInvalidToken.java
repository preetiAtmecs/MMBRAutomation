package com.ep.mmbr.api.testscripts.user;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.ep.mmbr.api.testscripts.TestSuiteBase;
import com.ep.mmbr.api.utilities.TestHandler;
import com.jayway.restassured.response.Response;

public class GetUserInfoWithInvalidToken extends TestSuiteBase {

	@Test
	public void testUserInfoWithInvalidToken()  {

		TestHandler testHandler = new TestHandler();
		JSONObject getUserRequestData = testHandler.readFileData("user",
				"getUserInfoWithInvalidToken.json");
		
		Reporter.log("Sending get user information with invalid token : "+getUserRequestData.get("uri"));
		
		String invalidsalesforceToken = "djgeuuih24u38702dh28fu28fyh28f84fhcjsj";
		Response getUserResponse = testHandler.sendRequestAndGetResponse(
				getUserRequestData, invalidsalesforceToken);

		testHandler.verifyResponseCode(getUserResponse,getUserRequestData.get("status").toString());

		Assert.assertEquals(getUserResponse.asString(),
				getUserRequestData.get("responseBody").toString());

	}

}

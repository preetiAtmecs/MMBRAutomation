package com.ep.mmbr.api.testscripts.user;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ep.mmbr.api.assertions.AssertTest;
import com.ep.mmbr.api.services.Service;
import com.ep.mmbr.api.services.ServiceFactory;
import com.ep.mmbr.api.testscripts.TestSuiteBase;
import com.ep.mmbr.api.utilities.MMBRConstants;
import com.ep.mmbr.api.utilities.TestHelper;
import com.jayway.restassured.response.Response;

public class GetUserInformationFromToken extends TestSuiteBase {

	JSONObject testData;

	@BeforeTest
	public void setup() {

		testData = new TestHelper().readFileData("User",
				"GetUserInformationFromToken.json");

	}

	@Test
	public void testUserInformationFromToken() throws JSONException {
		AssertTest assertTest = new AssertTest();

		System.out
				.println("\n GetUserInformationFromToken execution started..................................");

		Service service = new ServiceFactory().getService((String) testData
				.get(MMBRConstants.METHOD));

		Assert.assertNotNull(service, MMBRConstants.METHOD
				+ MMBRConstants.NOT_METHOD);

		Response response = service.getResponse(testData,
				CONFIG.getProperty("Token"));

		System.out.println("\nRespons code: " + response.getStatusCode());

		Assert.assertEquals(response.getStatusCode(),
				Integer.parseInt(testData.get("status").toString()));

		assertTest.assertJsonString(response.asString(),
				testData.get("response body").toString());

	}

}

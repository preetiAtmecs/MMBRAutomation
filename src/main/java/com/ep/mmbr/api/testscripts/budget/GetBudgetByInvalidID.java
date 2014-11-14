package com.ep.mmbr.api.testscripts.budget;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ep.mmbr.api.testscripts.TestSuiteBase;
import com.ep.mmbr.api.utilities.TestHandler;
import com.jayway.restassured.response.Response;

public class GetBudgetByInvalidID extends TestSuiteBase {
	JSONObject testData;

	@Test
	public void testGetBudgetByInvalidID() throws JSONException {
		TestHandler testHandler = new TestHandler();

		JSONObject getBudgetByInvalidIDRequestData = testHandler.readFileData(
				"budget", "getBudgetByInvalidID.json");

		Response getBudgetByIDResponse = testHandler.sendRequestAndGetResponse(
				getBudgetByInvalidIDRequestData,
				CONFIG.getProperty("SalesforceToken"));

		Assert.assertEquals(getBudgetByIDResponse.getStatusCode(), Integer
				.parseInt(getBudgetByInvalidIDRequestData.get("status")
						.toString()));

	}

}

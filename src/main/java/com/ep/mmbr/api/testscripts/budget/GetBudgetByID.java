package com.ep.mmbr.api.testscripts.budget;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ep.mmbr.api.testscripts.TestSuiteBase;
import com.ep.mmbr.api.utilities.TestHandler;
import com.jayway.restassured.response.Response;

public class GetBudgetByID extends TestSuiteBase {
	JSONObject testData;

	@Test
	public void testGetBudgetByID() throws JSONException {

		TestHandler testHandler = new TestHandler();

		JSONObject postBudgetRequestData = testHandler.readFileData("budget",
				"postBudget.json");

		Response postBudgetResponse = testHandler.sendRequestAndGetResponse(
				postBudgetRequestData, CONFIG.getProperty("SalesforceToken"));

		Assert.assertEquals(postBudgetResponse.getStatusCode(), Integer
				.parseInt(postBudgetRequestData.get("status").toString()));

		String budgetId = postBudgetResponse.jsonPath().getString("budgetId");

		JSONObject getBudgetByIDRequestData = testHandler.readFileData(
				"budget", "getBudgetByID.json");
		testHandler.replaceBudgetId(getBudgetByIDRequestData, budgetId);

		Response getBudgetByIDResponse = testHandler
				.sendRequestAndGetResponse(getBudgetByIDRequestData,
						CONFIG.getProperty("SalesforceToken"));

		Assert.assertEquals(getBudgetByIDResponse.getStatusCode(), Integer
				.parseInt(getBudgetByIDRequestData.get("status").toString()));

		String actualbudgetName = getBudgetByIDResponse.jsonPath().getString(
				"name");
		Assert.assertEquals(actualbudgetName,
				getBudgetByIDRequestData.get("budgetName").toString());

	}

}

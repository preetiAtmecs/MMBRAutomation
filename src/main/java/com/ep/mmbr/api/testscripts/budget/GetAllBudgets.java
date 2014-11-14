package com.ep.mmbr.api.testscripts.budget;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ep.mmbr.api.testscripts.TestSuiteBase;
import com.ep.mmbr.api.utilities.TestHandler;
import com.jayway.restassured.response.Response;

public class GetAllBudgets extends TestSuiteBase {
	JSONObject testData;

	@Test
	public void testGetAllBudgets() throws JSONException {
		TestHandler testHandler = new TestHandler();

		JSONObject getAllBudgetsRequestData = testHandler.readFileData(
				"budget", "getAllBudgets.json");

		Response getAllBudgetsResponse = testHandler
				.sendRequestAndGetResponse(getAllBudgetsRequestData,
						CONFIG.getProperty("SalesforceToken"));

		if (getAllBudgetsResponse.getStatusCode() == 500) {
			JSONObject postBudgetRequestData = testHandler.readFileData(
					"budget", "postBudget.json");

			Response postBudgetResponse = testHandler
					.sendRequestAndGetResponse(postBudgetRequestData,
							CONFIG.getProperty("SalesforceToken"));

			Assert.assertEquals(postBudgetResponse.getStatusCode(), Integer
					.parseInt(postBudgetRequestData.get("status").toString()));
		}

		Assert.assertEquals(getAllBudgetsResponse.getStatusCode(), Integer
				.parseInt(getAllBudgetsRequestData.get("status").toString()));

	}

}

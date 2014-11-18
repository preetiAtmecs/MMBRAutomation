package com.ep.mmbr.api.testscripts.budget;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.ep.mmbr.api.testscripts.TestSuiteBase;
import com.ep.mmbr.api.utilities.RequestDataBuilder;
import com.ep.mmbr.api.utilities.TestDataProvider;
import com.jayway.restassured.response.Response;

/**
 * This script validate response code for get all budgets with valid data.
 * 
 * @author pg092111
 *
 */
public class GetAllBudgets extends TestSuiteBase {

	/**
	 * Verifies if budgets are available for the user if not upload the budget 
	 * if available send request to get all budgets and verifies response code 200
	 * 
	 */
	@Test
	public void testGetAllBudgets()  {
		RequestDataBuilder requestDataBuilder = new RequestDataBuilder();

		TestDataProvider dataProvider = new TestDataProvider();
		JSONObject getAllBudgetsRequestData = dataProvider.readFileData(
				"budget", "getAllBudgets.json");

		Reporter.log("<br>Verifying if budgets are available for user");

		Response getAllBudgetsResponse = requestDataBuilder
				.sendRequestAndGetResponse(getAllBudgetsRequestData,
						CONFIG.getProperty("SalesforceToken"));

		if (getAllBudgetsResponse.getStatusCode() == 500) {
			Reporter.log("<br>No budgets found");

			requestDataBuilder.uploadBudget(CONFIG.getProperty("SalesforceToken"));

			getAllBudgetsResponse = requestDataBuilder.sendRequestAndGetResponse(
					getAllBudgetsRequestData,
					CONFIG.getProperty("SalesforceToken"));
		}

		Reporter.log("<br>Found budgets for user");

		Assert.assertTrue(requestDataBuilder.verifyResponseCode(
				getAllBudgetsResponse, getAllBudgetsRequestData.get("status")
						.toString()));

	}

}

package com.ep.mmbr.api.testscripts.budget;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.ep.mmbr.api.testscripts.TestSuiteBase;
import com.ep.mmbr.api.utilities.TestDataProvider;
import com.ep.mmbr.api.utilities.RequestHandler;
import com.jayway.restassured.response.Response;

/**
 * This script validate response code for get budget by valid budget id request.
 * 
 * @author pg092111
 * 
 */
public class GetBudgetByID extends TestSuiteBase {

	/**
	 * Uploads budget file,get budget id,sets budget id to request parameters
	 * Send request to get budget by id with valid budget id and verifies
	 * response code 200 Verifies received budget name in response matches with
	 * the uploaded budget name
	 */
	@Test
	public void testGetBudgetByID() throws JSONException {

		String salceforceToken = CONFIG.getProperty("SalesforceToken");
		RequestHandler requestHandler = new RequestHandler();
		TestDataProvider dataProvider = new TestDataProvider();
		
		JSONObject getBudgetByIDRequestData = dataProvider.readFileData(
				"budget", "getBudgetByID.json");

		// Before sending request for get budget by id , upload budget and get
		// the budget id
		String budgetId = requestHandler
				.uploadBudgetAndGetBudgetID(salceforceToken);

		requestHandler.setParameterValue(getBudgetByIDRequestData, "budget_id",
				budgetId);

		Reporter.log("<br><br>Sending reqest to get budget by id ");
		Response getBudgetByIDResponse = requestHandler
				.sendRequestAndGetResponse(getBudgetByIDRequestData,
						salceforceToken);

		Assert.assertTrue(requestHandler.verifyResponseCode(
				getBudgetByIDResponse, getBudgetByIDRequestData.get("status")
						.toString()));

		String actualbudgetName = getBudgetByIDResponse.jsonPath().getString("name");
		String expectedBudgetName = getBudgetByIDRequestData.get("budgetName")
				.toString();

		Reporter.log("<br>Verifying budget name :<br>Actual budget name : "
				+ actualbudgetName + "<br> Expected budget name : "
				+ expectedBudgetName);

		Assert.assertEquals(actualbudgetName, expectedBudgetName);

	}

}

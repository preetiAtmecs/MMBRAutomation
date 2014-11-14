package com.ep.mmbr.api.testscripts.budget;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.ep.mmbr.api.testscripts.TestSuiteBase;
import com.ep.mmbr.api.utilities.TestHandler;
import com.jayway.restassured.response.Response;

public class GetBudgetByID extends TestSuiteBase {

	@Test
	public void testGetBudgetByID() throws JSONException {

		TestHandler testHandler = new TestHandler();

		JSONObject getBudgetByIDRequestData = testHandler.readFileData("budget", "getBudgetByID.json");

		
		// Uploading budget to get the budget id
		Reporter.log("<br>Uploading budget: "+ getBudgetByIDRequestData.get("budgetName"));

		JSONObject postBudgetRequestData = testHandler.readFileData("budget","postBudget.json");
		Response postBudgetResponse = testHandler.sendRequestAndGetResponse(postBudgetRequestData, CONFIG.getProperty("SalesforceToken"));

	
		Assert.assertEquals(postBudgetResponse.getStatusCode(), Integer
				.parseInt(postBudgetRequestData.get("status").toString()));

		
		Reporter.log("<br>Budget uploaded successfully");
		String budgetId = postBudgetResponse.jsonPath().getString("budgetId");

		
		Reporter.log("<br>Extracted budget id for the budget: " + budgetId);
		testHandler.addBudgetId(getBudgetByIDRequestData, budgetId);

		
		Reporter.log("<br>Sending get reqest for get budget by id uri : "+ getBudgetByIDRequestData.get("uri"));
		Response getBudgetByIDResponse = testHandler.sendRequestAndGetResponse(getBudgetByIDRequestData,
						CONFIG.getProperty("SalesforceToken"));

		testHandler.verifyResponseCode(getBudgetByIDResponse,getBudgetByIDRequestData.get("status").toString());
				
		String actualbudgetName = getBudgetByIDResponse.jsonPath().getString("name");
		String expectedBudgetName = getBudgetByIDRequestData.get("budgetName").toString();
		
		
		Reporter.log("<br>Verifying budget name :<br>Actual budget name : " + actualbudgetName+ " Expected budget name : " + expectedBudgetName);

		Assert.assertEquals(actualbudgetName, expectedBudgetName);

	}

}

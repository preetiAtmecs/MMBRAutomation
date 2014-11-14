package com.ep.mmbr.api.testscripts.budget;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.ep.mmbr.api.testscripts.TestSuiteBase;
import com.ep.mmbr.api.utilities.TestHandler;
import com.jayway.restassured.response.Response;

public class GetAllBudgets extends TestSuiteBase {
	
	@Test
	public void testGetAllBudgets() throws JSONException {
		TestHandler testHandler = new TestHandler();

		
		JSONObject getAllBudgetsRequestData = testHandler.readFileData(
				"budget", "getAllBudgets.json");
		
		Reporter.log("<br>Verify if budgets are available for user");
		
		Response response = testHandler.sendRequestAndGetResponse(getAllBudgetsRequestData,
						CONFIG.getProperty("SalesforceToken"));

		if (response.getStatusCode() == 500) {
			Reporter.log("<br>No budgets found");
			
			JSONObject postBudgetRequestData = testHandler.readFileData(
					"budget", "postBudget.json");
			
			Reporter.log("<br>Uploading budget  ");
			Response postBudgetResponse = testHandler.sendRequestAndGetResponse(postBudgetRequestData,
							CONFIG.getProperty("SalesforceToken"));

			Assert.assertEquals(postBudgetResponse.getStatusCode(), Integer
					.parseInt(postBudgetRequestData.get("status").toString()));
		}

		Reporter.log("<br>Found budgets for user");
				
		Reporter.log("<br>Sending get reqest for get all budgets uri : "+getAllBudgetsRequestData.get("uri"));
		
		Response getAllBudgetsResponse = testHandler.sendRequestAndGetResponse(getAllBudgetsRequestData,
				CONFIG.getProperty("SalesforceToken"));
					
		testHandler.verifyResponseCode(getAllBudgetsResponse,getAllBudgetsRequestData.get("status").toString());
		
	}

}

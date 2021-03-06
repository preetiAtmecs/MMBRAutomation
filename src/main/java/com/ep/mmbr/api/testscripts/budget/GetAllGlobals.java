package com.ep.mmbr.api.testscripts.budget;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.ep.mmbr.api.testscripts.TestSuiteBase;
import com.ep.mmbr.api.utilities.RequestHandler;
import com.ep.mmbr.api.utilities.TestDataProvider;
import com.jayway.restassured.response.Response;

/**
 * This script validate response code to get all globals  by valid budget id and valid global group request.
 * 
 * @author pg092111
 * 
 */
public class GetAllGlobals extends TestSuiteBase {
	
	/**
	 * Upload budget and get budget id,set budget id and set valid global group id to get all globals request data
	 * Send request and verifies response code receives 200
	 */
	@Test
	public void testGetAllGlobals()  {
		String salesforceToekn = CONFIG.getProperty("SalesforceToken");
		RequestHandler requestHandler = new RequestHandler();

		JSONObject getGlobalGroupByIDRequestData = new TestDataProvider()
				.readFileData("budget", "GetAllGlobals.json");

		String budgetId = requestHandler
				.uploadBudgetAndGetBudgetID(salesforceToekn);
		
		String globalGroupId = requestHandler.getGlobalGroupIdFromBudget(
				budgetId, salesforceToekn);

		Reporter.log("<br><br>Set budget id and global id parametrs to request data : <br>Budgte ID:"
				+ budgetId + "<br>Global group id" + globalGroupId);
		
		getGlobalGroupByIDRequestData = requestHandler.setParameterValue(
				getGlobalGroupByIDRequestData, "budget_id", budgetId);
		
		getGlobalGroupByIDRequestData = requestHandler
				.setParameterValue(getGlobalGroupByIDRequestData,
						"global_group_id", globalGroupId);
		
		
		Reporter.log("<br><br>Sending reqest to get global group by valid budget id and valid global group id");
		Response getGlobalGroupByIDResponse = requestHandler
				.sendRequestAndGetResponse(getGlobalGroupByIDRequestData,
						CONFIG.getProperty("SalesforceToken"));

		Assert.assertTrue(requestHandler.verifyResponseCode(
				getGlobalGroupByIDResponse,
				getGlobalGroupByIDRequestData.get("status").toString()));
	}
}

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
		RequestDataBuilder requestDataBuilder = new RequestDataBuilder();

		JSONObject getGetAllGlobalsRequestData = new TestDataProvider()
				.readFileData("budget", "GetAllGlobals.json");

		String budgetId = requestDataBuilder
				.uploadBudgetAndGetBudgetID(salesforceToekn);
		
		String globalGroupId = requestDataBuilder.getGlobalGroupIdFromBudget(
				budgetId, salesforceToekn);

		Reporter.log("<br><br>Set budget id and global id parametrs to request data : <br>Budgte ID:"
				+ budgetId + "<br>Global group id" + globalGroupId);
		
		getGetAllGlobalsRequestData = requestDataBuilder.setParameterValue(
				getGetAllGlobalsRequestData, "budget_id", budgetId);
		
		getGetAllGlobalsRequestData = requestDataBuilder
				.setParameterValue(getGetAllGlobalsRequestData,
						"global_group_id", globalGroupId);
		
		
		Reporter.log("<br><br>Sending reqest to get global group by valid budget id and valid global group id");
		Response getAllGlobalsResponse = requestDataBuilder
				.sendRequestAndGetResponse(getGetAllGlobalsRequestData,
						CONFIG.getProperty("SalesforceToken"));

		Assert.assertTrue(requestDataBuilder.verifyResponseCode(
				getAllGlobalsResponse,
				getGetAllGlobalsRequestData.get("status").toString()));
	}
}

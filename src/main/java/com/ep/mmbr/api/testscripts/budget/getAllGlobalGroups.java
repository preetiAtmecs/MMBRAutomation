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
 * This script validate response code for get all global groups with valid data.
 * 
 * @author pg092111
 *
 */
public class getAllGlobalGroups extends TestSuiteBase {

	/**
	 * Uploads budget file,get budget id,sets budget id to request parameters
	 * Send request to get all global groups with valid budget id and verifies response code 200
	 * 
	 */
	@Test
	public void testGetAllGlobalGroups()  {
		
		RequestDataBuilder requestDataBuilder = new RequestDataBuilder();
		
		JSONObject getAllGlobalGroupsRequestData = new TestDataProvider().readFileData(
				"budget", "getAllGlobalGroups.json");

		
		String budgetId = requestDataBuilder.uploadBudgetAndGetBudgetID(CONFIG
				.getProperty("SalesforceToken"));

		requestDataBuilder.setParameterValue(getAllGlobalGroupsRequestData,"budget_id", budgetId);

		Reporter.log("<br><br>Sending request to get all global group by valid budget id");
		
		Response getBudgetByIDResponse = requestDataBuilder.sendRequestAndGetResponse(
				getAllGlobalGroupsRequestData,
				CONFIG.getProperty("SalesforceToken"));

		Assert.assertTrue(requestDataBuilder.verifyResponseCode(getBudgetByIDResponse,
				getAllGlobalGroupsRequestData.get("status").toString()));

	}
}

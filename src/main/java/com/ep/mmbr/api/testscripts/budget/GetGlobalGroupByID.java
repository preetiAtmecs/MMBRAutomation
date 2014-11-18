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
 * This script validate response code for get global by valid budget id ,global group id and global id request .
 * 
 * @author pg092111
 * 
 */
public class GetGlobalGroupByID extends TestSuiteBase {
	
	/**
	 * Upload budget and get budget id,set budget id as path  parameter to get  g global group id ,get global ifd from global group
	 * Set path parameters budgte is,global group id and global id in request data.
	 * Send request and verifies response code receives 200
	 */
	@Test
	public void testGetGlobalGroupByID()  {
		String salesforceToekn = CONFIG.getProperty("SalesforceToken");
		RequestDataBuilder requestDataBuilder = new RequestDataBuilder();

		JSONObject getGlobalGroupByIDRequestData = new TestDataProvider()
				.readFileData("budget", "GetGlobalGroupByID.json");

		String budgetId = requestDataBuilder
				.uploadBudgetAndGetBudgetID(salesforceToekn);
		
		String globalGroupId = requestDataBuilder.getGlobalGroupIdFromBudget(
				budgetId, salesforceToekn);

		Reporter.log("<br><br>Set budget id and global id parametrs to request data : <br>Budgte ID:"
				+ budgetId + "<br>Global group id" + globalGroupId);
		
		getGlobalGroupByIDRequestData = requestDataBuilder.setParameterValue(
				getGlobalGroupByIDRequestData, "budget_id", budgetId);
		
		getGlobalGroupByIDRequestData = requestDataBuilder
				.setParameterValue(getGlobalGroupByIDRequestData,
						"global_group_id", globalGroupId);
		
		
		Reporter.log("<br><br>Sending reqest to get global group by valid budget id and valid global group id");
		Response getGlobalGroupByIDResponse = requestDataBuilder
				.sendRequestAndGetResponse(getGlobalGroupByIDRequestData,
						CONFIG.getProperty("SalesforceToken"));

		Assert.assertTrue(requestDataBuilder.verifyResponseCode(
				getGlobalGroupByIDResponse,
				getGlobalGroupByIDRequestData.get("status").toString()));
	}
}

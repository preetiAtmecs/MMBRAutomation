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
 * This script validate response code for post valid global  with valid budget id and valid global group.
 * 
 * @author pg092111
 * 
 */
public class PostAGlobal extends TestSuiteBase {

	/**
	 * Upload budget and get budget id,set budget id and valid global group to post global  request data
	 * Send post request and verifies response code receives 201
	 */
	@Test
	public void testPostAGlobal()  {
		
		String salesforceToken = CONFIG.getProperty("SalesforceToken");
		RequestDataBuilder requestDataBuilder = new RequestDataBuilder();
		
		JSONObject postAGlobalRequestData = new TestDataProvider().readFileData(
				"budget", "postAGlobal.json");

		
		String budgetId = requestDataBuilder.uploadBudgetAndGetBudgetID(salesforceToken);

		String globalGroupId = requestDataBuilder.getGlobalGroupIdFromBudget(
				budgetId, salesforceToken);

		Reporter.log("<br><br>Set budget id and global id parametrs to request data : <br>Budgte ID:"
				+ budgetId + "<br>Global group id" + globalGroupId);
		
		postAGlobalRequestData = requestDataBuilder.setParameterValue(
				postAGlobalRequestData, "budget_id", budgetId);
		
		postAGlobalRequestData = requestDataBuilder
				.setParameterValue(postAGlobalRequestData,
						"global_group_id", globalGroupId);

		Reporter.log("<br><br>Sending post reqest with new global values");
		
				
		Response postGlobalGroupResponse = requestDataBuilder.sendRequestAndGetResponse(
				postAGlobalRequestData,salesforceToken);

		Assert.assertTrue(requestDataBuilder.verifyResponseCode(postGlobalGroupResponse,
				postAGlobalRequestData.get("status").toString()));

	}
}


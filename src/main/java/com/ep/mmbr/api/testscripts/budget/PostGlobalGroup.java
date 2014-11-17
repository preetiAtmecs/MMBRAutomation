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
 * This script validate response code for post valid global group with valid budget id.
 * 
 * @author pg092111
 * 
 */
public class PostGlobalGroup extends TestSuiteBase {

	/**
	 * Upload budget and get budget id,set budget id to post global group request data
	 * Send post request and verifies response code receives 201
	 */
	@Test
	public void testPostGlobalGroups()  {
		
		RequestHandler requestHandler = new RequestHandler();
		
		JSONObject postGlobalGroupRequestData = new TestDataProvider().readFileData(
				"budget", "postGlobalGroup.json");

		
		String budgetId = requestHandler.uploadBudgetAndGetBudgetID(CONFIG
				.getProperty("SalesforceToken"));

		postGlobalGroupRequestData =	requestHandler.setParameterValue(postGlobalGroupRequestData,"budget_id", budgetId);

		Reporter.log("<br><br>Sending post reqest with new global group ");
		
				
		Response postGlobalGroupResponse = requestHandler.sendRequestAndGetResponse(
				postGlobalGroupRequestData,
				CONFIG.getProperty("SalesforceToken"));

		Assert.assertTrue(requestHandler.verifyResponseCode(postGlobalGroupResponse,
				postGlobalGroupRequestData.get("status").toString()));

	}
}


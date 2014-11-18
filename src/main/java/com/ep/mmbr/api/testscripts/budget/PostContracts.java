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
 * This script validate response code for post valid contracts with valid budget id.
 * 
 * @author pg092111
 * 
 */
public class PostContracts extends TestSuiteBase {

	/**
	 * Upload budget and get budget id,set budget id to postpost contracts request data
	 * Send post request and verifies response code receives 200
	 */
	@Test
	public void testPostContracts()  {
		
		String token = CONFIG
				.getProperty("SalesforceToken");
		RequestDataBuilder requestDataBuilder = new RequestDataBuilder();
		
		JSONObject postContractsRequestData = new TestDataProvider().readFileData(
				"budget", "postContracts.json");

		
		String budgetId = requestDataBuilder.uploadBudgetAndGetBudgetID(token);

		postContractsRequestData =	requestDataBuilder.setParameterValue(postContractsRequestData,"budget_id", budgetId);

		Reporter.log("<br><br>Sending post request with contracts ");
		
				
		Response postContractsResponse = requestDataBuilder.sendRequestAndGetResponse(
				postContractsRequestData,token);

		Assert.assertTrue(requestDataBuilder.verifyResponseCode(postContractsResponse,
				postContractsRequestData.get("status").toString()));

	}
}


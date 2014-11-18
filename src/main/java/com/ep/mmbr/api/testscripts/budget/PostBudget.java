package com.ep.mmbr.api.testscripts.budget;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.ep.mmbr.api.testscripts.TestSuiteBase;
import com.ep.mmbr.api.utilities.TestDataProvider;
import com.ep.mmbr.api.utilities.RequestDataBuilder;
import com.jayway.restassured.response.Response;

/**
 * This script validate response code for post budget with valid .mbd file request.
 * 
 * @author pg092111
 * 
 */
public class PostBudget extends TestSuiteBase {

	/**
	 * Upload budget and verifies response code receives 200
	 */
	@Test
	public void testPostBudget() throws JSONException {
		RequestDataBuilder requestDataBuilder = new RequestDataBuilder();
		
		JSONObject postBudgetRequestData = new TestDataProvider().readFileData("budget",
				"postBudget.json");

		Reporter.log("Sending request to post budget ");
			
		Response postBudgetResponse = requestDataBuilder.uploadBudget(CONFIG.getProperty("SalesforceToken"));

		Assert.assertTrue(requestDataBuilder.verifyResponseCode(postBudgetResponse,postBudgetRequestData.get("status").toString()));
		
	}

}

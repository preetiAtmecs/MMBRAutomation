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
 * This script validate response code for get budget by invalid budget id request.
 * 
 * @author pg092111
 * 
 */
public class GetBudgetByInvalidID extends TestSuiteBase {
	
	/**
	 * Send request to get budget by id with invalid budget id and verifies
	 * response code match with 404 
	 */
	@Test
	public void testGetBudgetByInvalidID()  {
		RequestDataBuilder requestDataBuilder = new RequestDataBuilder();

		JSONObject getBudgetByInvalidIDRequestData = new TestDataProvider().readFileData(
				"budget", "getBudgetByInvalidID.json");
		
		Reporter.log("<br>Sending get reqest for get budget by invalid bidget id ");
		
		Response getBudgetByIDResponse = requestDataBuilder.sendRequestAndGetResponse(getBudgetByInvalidIDRequestData,
				CONFIG.getProperty("SalesforceToken"));
		
		Assert.assertTrue(requestDataBuilder.verifyResponseCode(getBudgetByIDResponse,getBudgetByInvalidIDRequestData.get("status").toString()));
		
	}

}

package com.ep.mmbr.api.testscripts.budget;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.ep.mmbr.api.testscripts.TestSuiteBase;
import com.ep.mmbr.api.utilities.TestHandler;
import com.jayway.restassured.response.Response;

public class GetBudgetByInvalidID extends TestSuiteBase {
	

	@Test
	public void testGetBudgetByInvalidID() throws JSONException {
		TestHandler testHandler = new TestHandler();

		JSONObject getBudgetByInvalidIDRequestData = testHandler.readFileData(
				"budget", "getBudgetByInvalidID.json");
		
		Reporter.log("Sending get reqest for get budget by invalid id uri : "+getBudgetByInvalidIDRequestData.get("uri"));
		
		Response getBudgetByIDResponse = testHandler.sendRequestAndGetResponse(getBudgetByInvalidIDRequestData,
				CONFIG.getProperty("SalesforceToken"));
		
		testHandler.verifyResponseCode(getBudgetByIDResponse,getBudgetByInvalidIDRequestData.get("status").toString());
		
	}

}

package com.ep.mmbr.api.testscripts.budget;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.ep.mmbr.api.testscripts.TestSuiteBase;
import com.ep.mmbr.api.utilities.TestHandler;
import com.jayway.restassured.response.Response;

public class PostBudget extends TestSuiteBase {

	@Test
	public void testPostBudget() throws JSONException {
		TestHandler testHandler = new TestHandler();
		
		JSONObject postBudgetRequestData = testHandler.readFileData("budget",
				"postBudget.json");

		Reporter.log("Sending post budget reqest uri : "+postBudgetRequestData.get("uri"));
		
		Response postBudgetResponse = testHandler.sendRequestAndGetResponse(
				postBudgetRequestData, CONFIG.getProperty("SalesforceToken"));

		testHandler.verifyResponseCode(postBudgetResponse,postBudgetRequestData.get("status").toString());
		
	}

}

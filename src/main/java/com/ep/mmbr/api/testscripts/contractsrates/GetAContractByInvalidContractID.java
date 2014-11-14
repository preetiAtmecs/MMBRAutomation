package com.ep.mmbr.api.testscripts.contractsrates;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.ep.mmbr.api.testscripts.TestSuiteBase;
import com.ep.mmbr.api.utilities.TestHandler;
import com.jayway.restassured.response.Response;

public class GetAContractByInvalidContractID extends TestSuiteBase {
	JSONObject testData;

	@Test
	public void testGetAContractByInvalidContractID() throws JSONException {

		TestHandler testHandler = new TestHandler();

		
		JSONObject getAContractRateByIDRequestData = testHandler.readFileData(
				"contractRates", "getAContractByInvalidContractID.json");

		Reporter.log("Sending get reqest for get contract by invalid id uri : "+getAContractRateByIDRequestData.get("uri"));
		
		Response getAContractRateByIDResponse = testHandler
				.sendRequestAndGetResponse(getAContractRateByIDRequestData,
						CONFIG.getProperty("SalesforceToken"));

		testHandler.verifyResponseCode(getAContractRateByIDResponse,getAContractRateByIDRequestData.get("status").toString());
	}
}

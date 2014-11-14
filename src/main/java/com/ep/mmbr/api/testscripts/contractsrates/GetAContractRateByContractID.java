package com.ep.mmbr.api.testscripts.contractsrates;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.ep.mmbr.api.testscripts.TestSuiteBase;
import com.ep.mmbr.api.utilities.TestHandler;
import com.jayway.restassured.response.Response;

public class GetAContractRateByContractID extends TestSuiteBase {
	JSONObject testData;

	@Test
	public void testGetAContractRateByID() throws JSONException {
		TestHandler testHandler = new TestHandler();

		JSONObject getAContractRateByIDRequestData = testHandler.readFileData(
				"contractRates", "getAContractRateByContractID.json");
		
		Reporter.log("Sending get reqest for get contract by id uri : "+getAContractRateByIDRequestData.get("uri"));
		
		Response getAContractRateByIDResponse = testHandler
				.sendRequestAndGetResponse(getAContractRateByIDRequestData,
						CONFIG.getProperty("SalesforceToken"));

		Assert.assertEquals(getAContractRateByIDResponse.getStatusCode(),
				Integer.parseInt(getAContractRateByIDRequestData.get("status")
						.toString()));
		
		testHandler.verifyResponseCode(getAContractRateByIDResponse,getAContractRateByIDRequestData.get("status").toString());
	}

}

package com.ep.mmbr.api.testscripts.contractsrates;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.ep.mmbr.api.testscripts.TestSuiteBase;
import com.ep.mmbr.api.utilities.TestDataProvider;
import com.ep.mmbr.api.utilities.RequestHandler;
import com.jayway.restassured.response.Response;

/**
 * This script validate response code for get contract rate by invalid contract
 * id request.
 * 
 * @author pg092111
 * 
 */
public class GetAContractByInvalidContractID extends TestSuiteBase {

	/**
	 * Send get contract request with invalid contract id and Verifies the
	 * response code matches with 404
	 * 
	 */
	@Test
	public void testGetAContractByInvalidContractID() throws JSONException {

		RequestHandler requestHandler = new RequestHandler();

		JSONObject getAContractRateByIDRequestData = new TestDataProvider()
				.readFileData("contractRates",
						"getAContractByInvalidContractID.json");

		Reporter.log("Sending reqest to get contract by invalid id ");

		Response getAContractRateByIDResponse = requestHandler
				.sendRequestAndGetResponse(getAContractRateByIDRequestData,
						CONFIG.getProperty("SalesforceToken"));

		Assert.assertTrue(requestHandler.verifyResponseCode(
				getAContractRateByIDResponse, getAContractRateByIDRequestData
						.get("status").toString()));
	}
}

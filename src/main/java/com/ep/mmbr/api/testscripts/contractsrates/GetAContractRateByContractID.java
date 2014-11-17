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
 * This script validate response code for get contract rate by valid contract
 * id request.
 * 
 * @author pg092111
 * 
 */
public class GetAContractRateByContractID extends TestSuiteBase {

	/**
	 * Send get contract request with valid contract id and Verifies the
	 * response code matches with 200
	 * 
	 */
	@Test
	public void testGetAContractRateByID() throws JSONException {
		RequestHandler requestHandler = new RequestHandler();

		JSONObject getAContractRateByIDRequestData = new TestDataProvider()
				.readFileData("contractRates",
						"getAContractRateByContractID.json");

		Reporter.log("Sending get reqest to get contract by id ");

		Response getAContractRateByIDResponse = requestHandler
				.sendRequestAndGetResponse(getAContractRateByIDRequestData,
						CONFIG.getProperty("SalesforceToken"));

		Assert.assertTrue(requestHandler.verifyResponseCode(
				getAContractRateByIDResponse, getAContractRateByIDRequestData
						.get("status").toString()));
	}

}

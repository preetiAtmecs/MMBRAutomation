package com.ep.mmbr.api.testscripts.contractsrates;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.ep.mmbr.api.testscripts.TestSuiteBase;
import com.ep.mmbr.api.utilities.RequestDataBuilder;
import com.ep.mmbr.api.utilities.TestDataProvider;
import com.jayway.restassured.response.Response;

/**
 * This script validate response code for get contract rate by invalid contract
 * id request.
 * 
 * @author pg092111
 * 
 */
public class GetAContractByInvalidValues extends TestSuiteBase {

	/**
	 * Send get contract request with invalid contract id and Verifies the
	 * response code matches with 404
	 * 
	 */
	@Test
	public void testGetAContractByInvalidContractID()  {

		RequestDataBuilder requestDataBuilder = new RequestDataBuilder();

		JSONObject getAContractRateByIDRequestData = new TestDataProvider()
				.readFileData("contractRates",
						"getAContractByInvalidContractID.json");

		Reporter.log("Sending reqest to get contract by invalid id ");

		Response getAContractRateByIDResponse = requestDataBuilder
				.sendRequestAndGetResponse(getAContractRateByIDRequestData,
						CONFIG.getProperty("SalesforceToken"));

		Assert.assertTrue(requestDataBuilder.verifyResponseCode(
				getAContractRateByIDResponse, getAContractRateByIDRequestData
						.get("status").toString()));
	}
	
	
}

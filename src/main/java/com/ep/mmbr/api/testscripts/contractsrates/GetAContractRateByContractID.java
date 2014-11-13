package com.ep.mmbr.api.testscripts.contractsrates;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ep.mmbr.api.assertions.AssertTest;
import com.ep.mmbr.api.services.Service;
import com.ep.mmbr.api.services.ServiceFactory;
import com.ep.mmbr.api.testscripts.TestSuiteBase;
import com.ep.mmbr.api.utilities.MMBRConstants;
import com.ep.mmbr.api.utilities.TestHelper;
import com.jayway.restassured.response.Response;

public class GetAContractRateByContractID extends TestSuiteBase {
	JSONObject testData;

	@BeforeTest
	public void setup() {

		testData = new TestHelper().readFileData("contractRates",
				"getAContractRateByContractID.json");

	}

	@Test
	public void testGetGetAContractRateByID() throws JSONException {
		
		System.out
				.println("\nTestGetAContractRateByContractID execution started..........................");

		Service service = new ServiceFactory().getService((String) testData
				.get(MMBRConstants.METHOD));

		Assert.assertNotNull(service, MMBRConstants.METHOD
				+ MMBRConstants.NOT_METHOD);

		Response response = service.getResponse(testData,
				CONFIG.getProperty("Token"));

		System.out.println("\nRespons code: " + response.getStatusCode());

		Assert.assertEquals(response.getStatusCode(),
				Integer.parseInt(testData.get("status").toString()));

		
		
		System.out.println("\nRespons body: " + response.asString());
		
		JSONObject responsedata =  (JSONObject) testData
			.get("response body");
		
		List<Map.Entry<String, String>> responseList = new ArrayList<Map.Entry<String, String>>(
				responsedata.entrySet()); 
		for (Map.Entry<String, String> option : responseList) {

			String expectedKey = option.getKey();

			String expectedValue = option.getValue();

			String actualValue = response.jsonPath().getString(expectedKey);
			
			Assert.assertNotNull(actualValue);
			
			Assert.assertEquals(actualValue, expectedValue);
		}
	}
}

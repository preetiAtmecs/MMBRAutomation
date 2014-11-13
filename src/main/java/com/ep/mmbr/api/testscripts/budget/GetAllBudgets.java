package com.ep.mmbr.api.testscripts.budget;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ep.mmbr.api.services.Service;
import com.ep.mmbr.api.services.ServiceFactory;
import com.ep.mmbr.api.testscripts.TestSuiteBase;
import com.ep.mmbr.api.utilities.MMBRConstants;
import com.ep.mmbr.api.utilities.TestHelper;
import com.ep.qa.automation.assertion.AssertionHandler;
import com.jayway.restassured.response.Response;

public class GetAllBudgets extends TestSuiteBase {
	JSONObject testData;

	@BeforeTest
	public void setup() {

		testData = new TestHelper().readFileData("budget",
				"getAllBudgets.json");

	}

	@Test
	public void testGetAllBudgets() throws JSONException {
		System.out.println("\nTestGetAllBudgets execution started..................................");
		
		Service service = new ServiceFactory().getService((String) testData
				.get(MMBRConstants.METHOD));

		Assert.assertNotNull(service, MMBRConstants.METHOD
				+ MMBRConstants.NOT_METHOD);

		Response response = service.getResponse(testData,
				CONFIG.getProperty("Token"));
		
		System.out.println("\nRespons code: "+response.getStatusCode());
		
		Assert.assertEquals(response.getStatusCode(),
				Integer.parseInt(testData.get("status").toString()));
		
		
		
		String responseBudgetNames = response.jsonPath().getString("name");
		
		System.out.println("\nRespons code: "+responseBudgetNames);

		new  AssertionHandler().assertJsonEquals(testData.get("budgetNames").toString(),responseBudgetNames,false);
		

	}

}


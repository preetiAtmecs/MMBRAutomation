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
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class PostBudget extends TestSuiteBase {

	JSONObject testData;
	DBCollection collection;
	BasicDBObject query = new BasicDBObject();

	@BeforeTest
	public void setup() {
		RestAssured.useRelaxedHTTPSValidation();

		testData = new TestHelper().readFileData("budget",
				"postBudget.json");
		System.out.println("testData " + testData);

	}

	@Test
	public void testPostBudgetWithExistingFile() throws JSONException {

		System.out
				.println("\nTest testPostBudgetWithExistingFile execution started...............................");

		collection = dbConnection.getDataBaseCollection(testData
				.get("budgetDB").toString(), testData.get("collectionName")
				.toString());

		query.put("name", testData.get("userName"));

		int beforePostCount = dbConnection.getCount(collection, query);
		System.out.println("\nBefore post budget count: " + beforePostCount);

		Service service = new ServiceFactory().getService((String) testData
				.get(MMBRConstants.METHOD));

		Assert.assertNotNull(service, MMBRConstants.METHOD
				+ MMBRConstants.NOT_METHOD);

		Response response = service.getResponse(testData,
				CONFIG.getProperty("Token"));

		System.out.println("\nRespons code: " + response.getStatusCode());

		Assert.assertEquals(response.getStatusCode(),
				Integer.parseInt(testData.get("status").toString()));

		int afterPostCount = dbConnection.getCount(collection, query);
		System.out.println("\nAfter post budget count: " + afterPostCount);

		Assert.assertEquals(afterPostCount, beforePostCount);

	}

}

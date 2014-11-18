package com.ep.mmbr.api.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;

import com.ep.mmbr.api.services.Service;
import com.ep.mmbr.api.services.ServiceFactory;
import com.ep.qa.automation.entities.DataProvider;
import com.jayway.restassured.response.Response;

/**
 * This class provides generic methods for building request data
 * 
 * @author Preethi
 * 
 */

public class RequestDataBuilder {

	DataProvider dataprovider = DataProvider.getInstance();

	/***
	 * Function to send request and get response using rest assured client
	 * 
	 * @param json
	 *            Object and salesforce token
	 * @return rest assure response
	 */
	public Response sendRequestAndGetResponse(JSONObject requestDataObject,
			String salesforceToken) {
		String requestMethod = requestDataObject.get("method").toString();

		Reporter.log("<br>Sending " + requestDataObject.get("method")
				+ " request ");
		Service service = new ServiceFactory().getService(requestMethod);

		Assert.assertNotNull(service, requestMethod + ":is not a http method");

		Reporter.log("<br>Endpoint : " + requestDataObject.get("uri"));
		Response response = service.sendRequestAndgetResponse(
				requestDataObject, salesforceToken);
		return response;
	}

	/**
	 * Function to set dynamic values in requestParametrs of the request data
	 * 
	 * @param requestDataObject
	 * @param parameterKey
	 * @param expectedValue
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONObject setParameterValue(JSONObject requestDataObject,
			String parameterKey, String expectedValue) {

		JSONObject requestBody = (JSONObject) requestDataObject
				.get("requestParameters");

		requestBody.put(parameterKey, expectedValue);

		requestDataObject.put("requestParameters", requestBody);

		return requestDataObject;
	}

	/**
	 * Verifies response code and returns true or false
	 * 
	 * @param requestDataObject
	 * @param parameterKey
	 * @param expectedValue
	 * @return
	 */
	public boolean verifyResponseCode(Response response,
			String expecteStatusCode) {
		Reporter.log("<br><br>Received Response body:</b>"
				+ response.asString());

		int actualResponseCode = response.getStatusCode();
		int expectedResponseCode = Integer.parseInt(expecteStatusCode);

		Reporter.log("<br><br>Verifying Response code : <br>"
				+ "Actual response code : " + actualResponseCode
				+ "<br>Expected response code : " + expectedResponseCode);

		if (actualResponseCode == expectedResponseCode) {
			return true;
		} else {
			Reporter.log("<br> Assertion failed ");
			return false;
		}
	}

	/**
	 * Method to upload budget file and returns budget id on success
	 * 
	 * @param requestDataObject
	 * @param parameterKey
	 * @param expectedValue
	 * @return
	 */
	public Response uploadBudget(String salesforceToken) {

		TestDataProvider dataProvider = new TestDataProvider();
		JSONObject postBudgetRequestData = dataProvider.readFileData("budget",
				"postBudget.json");
		
		Reporter.log("<br>Uploading budget: "
				+ postBudgetRequestData.get("budgetName"));

		Response postBudgetResponse = sendRequestAndGetResponse(
				postBudgetRequestData, salesforceToken);

		Assert.assertEquals(postBudgetResponse.getStatusCode(), Integer
				.parseInt(postBudgetRequestData.get("status").toString()));

		Reporter.log("<br>Budget uploaded successfully");
		return postBudgetResponse;

	}

	public String uploadBudgetAndGetBudgetID(String salesforceToken) {
		Response response = uploadBudget(salesforceToken);
		String budgetId = response.jsonPath().getString("budgetId");

		Reporter.log("<br>Received budget id  : " + budgetId);
		return budgetId;
	}

	/**
	 * Method to get global group id from the budget given
	 * 
	 * @param requestDataObject
	 * @param parameterKey
	 * @param expectedValue
	 * @return
	 */

	public String getGlobalGroupIdFromBudget(String budgetId,
			String salesforceToekn) {

		JSONObject getAllGlobalGroupsRequestData = new TestDataProvider()
				.readFileData("budget", "getAllGlobalGroups.json");

		getAllGlobalGroupsRequestData = setParameterValue(
				getAllGlobalGroupsRequestData, "budget_id", budgetId);

		Reporter.log("<br><br>Verifying  available global groups to get global group id");
		Response getAllGlobalGroupsResponse = sendRequestAndGetResponse(
				getAllGlobalGroupsRequestData, salesforceToekn);

		String globalGroupId = getAllGlobalGroupsResponse.jsonPath().getString(
				"id");
		globalGroupId = globalGroupId.substring(1, globalGroupId.length() - 1);
		Reporter.log("<br>Received global group id : " + globalGroupId);

		return globalGroupId;
	}

	/**
	 * Extract first global id from given global group response body
	 * 
	 * @param response
	 * @return
	 */
	public String getGlobalId(Response response) {
		
		Reporter.log("<br><br>Verifying vailable global in global group");
		String globalIDRawResult = response.jsonPath().getString("id");
		String replace = globalIDRawResult.replace("[", "");
		String replace1 = replace.replace("]", "");
		List<String> globalIDs = new ArrayList<String>(Arrays.asList(replace1
				.split(",")));
		Reporter.log("<br>Found "+globalIDs.size()+"globals in global group");
		String globalId = globalIDs.get(0);
		Reporter.log("<br>Get first indexed global id: "+globalId);

		return globalId;
	}
}

package com.ep.mmbr.api.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
	 * @param Request test data object (To send request and response from restassured)
	 * @param salesforce token (Authorization bearer)
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
	 * Function to set dynamic values in requestParametrs or body section of the request data
	 * 
	 * @param requestDataObject (json request Data Object containing requestParametrs or body section)
	 * @param locationName (requestParametrs or body section where the key is available) 
	 * @param parameterKey (key name to set the value) 
	 * @param expectedValue (expected value to set)
	 * @return requestDataObject (Same json request data object with modified values)
	 */
	@SuppressWarnings("unchecked")
	public JSONObject setValueInRequestData(JSONObject requestDataObject,
			String locationName, String parameterKey, String expectedValue) {

		JSONObject requestBody = (JSONObject) requestDataObject
				.get(locationName);

		requestBody.put(parameterKey, expectedValue);

		requestDataObject.put(locationName, requestBody);

		return requestDataObject;
	}

	/**
	 * Verifies actual response code with expected response code and returns true or false boolean value
	 * 
	 * @param responseBody to get the response code 
	 * @param expecte response code 
	 * @return true or false
	 */
	public boolean verifyResponseCode(Response responseBody,
			String expecteStatusCode) {
		Reporter.log("<br><br>Received Response body:</b>"
				+ responseBody.asString());

		int actualResponseCode = responseBody.getStatusCode();
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
	 * @param salesforce token (Authorization bearer)
	 * @return rest assure result response 
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

	/**
	 * Method to call upload budget method and returns budget id from the budget response
	 * 
	 * @param salesforce token (Authorization bearer)
	 * @return budget id
	 */
	public String uploadBudgetAndGetBudgetID(String salesforceToken) {
		Response response = uploadBudget(salesforceToken);
		String budgetId = response.jsonPath().getString("budgetId");

		Reporter.log("<br>Received budget id  : " + budgetId);
		return budgetId;
	}

	/**
	 * Method to get global group id from the budget given
	 * @param budget id (T find o get global group id from available groups in given budget)
	 * @param salesforce token (Authorization bearer)
	 * @return global group id
	 */

	public String getGlobalGroupIdFromBudget(String budgetId,
			String salesforceToekn) {

		JSONObject getAllGlobalGroupsRequestData = new TestDataProvider()
				.readFileData("budget", "getAllGlobalGroups.json");

		getAllGlobalGroupsRequestData = setValueInRequestData(
				getAllGlobalGroupsRequestData, "requestParameters",
				"budget_id", budgetId);

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
	 * @param responseBody to get the available globals in global group 
	 * @return first found global id
	 */
	public String getGlobalId(Response responseBody) {

		Reporter.log("<br><br>Verifying vailable global in global group");

		List<String> globalIDs = responseBody.jsonPath().getList("id");
		Reporter.log("<br>Found " + globalIDs.size()
				+ "globals in global group");
		String globalId = String.valueOf(globalIDs.get(0));
		Reporter.log("<br>Get first indexed global id: " + globalId);

		return globalId;
	}

	
	/**
	 * Get specific document from response body which contains the expected key name and value
	 * @param responseBody to find the expected document with given key value 
	 * @return provides json body where expected key and value is avalable
	 */
	public JSONObject getDocumnetFromResponse(Response response, String keyName,
			String valueName)  {

		JSONObject responseDetails = null;
		JSONParser p = new JSONParser();
		JSONArray responseJsonBody = new JSONArray();

		try {
			responseJsonBody = (JSONArray) p.parse(response.jsonPath().prettify());
		} catch (ParseException e) {
			Reporter.log("<brFailed to parse response body to json array");
					}

		for (int document = 0; document < responseJsonBody.size(); document++) {
			JSONObject documentDetails = (JSONObject) responseJsonBody
					.get(document);

			List<Map.Entry<String, String>> documents = new ArrayList<Map.Entry<String, String>>(
					documentDetails.entrySet());

			for (Map.Entry<String, String> eachDocument : documents) {

				String key = eachDocument.getKey();

				Object value = eachDocument.getValue();

				if (key.equals(keyName) && value.toString().equals(valueName)) {
					Reporter.log("<br>Document found with " + key + " :"
							+ value);
					responseDetails = documentDetails;
					break;
				}
			}
			if (responseDetails != null) {
				break;
			}
		}
		
		return responseDetails;
	}
}

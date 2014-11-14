/****
 * This is the test handler for all the test cases for mm.io build.
 * It provide generic methods to provide the service
 */
package com.ep.mmbr.api.utilities;

import java.io.File;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;

import com.ep.mmbr.api.services.Service;
import com.ep.mmbr.api.services.ServiceFactory;
import com.ep.qa.automation.entities.DataProvider;
import com.jayway.restassured.response.Response;

public class TestHandler {

	DataProvider dataprovider = DataProvider.getInstance();

	/***
	 * Function to get read the file from testData folder and return jsonObject
	 * 
	 * @param folderName (example: budget or user or contract)
	 * @param fileName    (example: xyz.json)
	 * @return
	 */
	public JSONObject readFileData(String folderName, String fileName) {
		JSONObject requestDataObject = null;

		String fileSeparator = System.getProperty("file.separator");

		String path = "testData" + fileSeparator + folderName + fileSeparator
				+ fileName;

		File file = dataprovider.getFile(path);
		requestDataObject = dataprovider.getJSONObject(file);

		return requestDataObject;
	}

	/***
	 * Function to send request and get response using rest assured
	 * 
	 * @param json Object and salesforce token
	 * @return rest assure response
	 */
	public Response sendRequestAndGetResponse(JSONObject requestDataObject,
			String salesforceToken) {
		String requestMethod = requestDataObject.get("method").toString();

		Service service = new ServiceFactory().getService(requestMethod);

		Assert.assertNotNull(service, requestMethod + ":is not a http method");

		Response response = service.getResponse(requestDataObject,
				salesforceToken);
		return response;
	}

	/***
	 * Function to add dynamic budget id in requestParametrs of request data
	 * json Object
	 * 
	 * @param json object with request parameters
	 *            and budget id to replace
	 * @return requestDataObject (changed)
	 */
	public JSONObject addBudgetId(JSONObject requestDataObject,
			String expectedValue) {

		JSONObject requestBody = (JSONObject) requestDataObject
				.get("requestParameters");

		requestBody.put("budget_id", expectedValue);

		requestDataObject.put("requestParameters", requestBody);

		return requestDataObject;
	}

	
	
	public boolean verifyResponseCode(Response response,String expecteStatusCode)
	{
		Reporter.log("<br><br>Received Response body:</b>"+ response.asString());
		
		
		int actualResponseCode = response.getStatusCode();
		int expectedResponseCode = Integer.parseInt(expecteStatusCode);
		
		
		Reporter.log("<br><br>Verifying Response code : <br>" + "Actual response code : "+actualResponseCode
				+ "<br>Expected response code : " + expectedResponseCode);

		if(actualResponseCode == expectedResponseCode){return true;}else{return false;}
	}
}

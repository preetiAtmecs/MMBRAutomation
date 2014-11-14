package com.ep.mmbr.api.utilities;

import java.io.File;

import org.json.simple.JSONObject;
import org.testng.Assert;

import com.ep.mmbr.api.services.Service;
import com.ep.mmbr.api.services.ServiceFactory;
import com.ep.qa.automation.entities.DataProvider;
import com.jayway.restassured.response.Response;

public class TestHandler {

	DataProvider dataprovider = DataProvider.getInstance();

	public JSONObject readFileData(String folderName, String fileName) {
		JSONObject testCaseObject = null;

		String fileSeparator = System.getProperty("file.separator");

		String path = "testData" + fileSeparator + folderName + fileSeparator
				+ fileName;

		File file = dataprovider.getFile(path);
		testCaseObject = dataprovider.getJSONObject(file);

		return testCaseObject;
	}

	public Response sendRequestAndGetResponse(JSONObject requestDataObject,
			String salesforceToken) {
		String requestMethod = requestDataObject.get("method").toString();

		Service service = new ServiceFactory().getService(requestMethod);
		Assert.assertNotNull(service, requestMethod + ":is not a http method");
		Response response = service.getResponse(requestDataObject,
				salesforceToken);
		return response;
	}

	public JSONObject replaceBudgetId(JSONObject expectedJsonData,
			String expectedValue) {

		JSONObject requestBody = (JSONObject) expectedJsonData
				.get("requestParameters");
		requestBody.put("budget_id", expectedValue);
		expectedJsonData.put("requestParameters", requestBody);
		System.out.println("\nexpectedJsonData" + expectedJsonData);
		return expectedJsonData;
	}

}

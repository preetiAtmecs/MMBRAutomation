package com.ep.mmbr.api.utilities;

import java.io.File;

import org.json.simple.JSONObject;
import org.testng.Reporter;

import com.ep.qa.automation.entities.DataProvider;

/**
 * This is the handles to provide request data for all the test cases for mm.io
 * build.
 * 
 * @author preethi
 * 
 */
public class TestDataProvider {

	DataProvider dataprovider = DataProvider.getInstance();

	/***
	 * Function to get read the file from testData folder and return jsonObject
	 * 
	 * @param folderName   (example: budget or user or contract)
	 * @param fileName             (example: xyz.json)
	 * @return
	 * @throws Exception
	 */
	public JSONObject readFileData(String folderName, String fileName) {
		JSONObject requestDataObject = null;

		String fileSeparator = System.getProperty("file.separator");

		String path = "testData" + fileSeparator + folderName + fileSeparator
				+ fileName;

		File file = dataprovider.getFile(path);
		try {
			requestDataObject = dataprovider.getJSONObject(file);

		} catch (Exception e) {
			Reporter.log("Failed to read request test data"
					+ e.fillInStackTrace());
		}
		return requestDataObject;
	}

}

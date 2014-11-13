package com.ep.mmbr.api.utilities;

import java.io.File;

import org.json.simple.JSONObject;

import com.ep.qa.automation.entities.DataProvider;



public class TestHelper {
	
	
	DataProvider dataprovider = DataProvider.getInstance();
	
	public JSONObject readFileData(String folderName, String fileName) {
		JSONObject testCaseObject =null;

		
		String fileSeparator = System.getProperty("file.separator");
		//String path = System.getProperty("user.dir") + fileSeparator + "testData" + fileSeparator+folderName+ fileSeparator + fileName;
		String path =  "testData" + fileSeparator+folderName+ fileSeparator + fileName;
		
		File file = dataprovider.getFile(path);
		testCaseObject = dataprovider.getJSONObject(file);

		return testCaseObject;
	}

	
	
	
}

package com.ep.mmbr.api.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.Reporter;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.specification.RequestSpecification;

/**
 * This class is responsible for building request builder based  * on the content provided
 * @author preethi
 *
 */
public class RequestSpecificationBuilder {

	/**
	 * Method builds request specification that will sent with request
	 * @param paramType
	 * @param requestBody
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public RequestSpecification getRequestSpecification(String paramType,
			Object requestBody) {

		JSONObject body = (JSONObject) requestBody;
		List<Map.Entry<String, String>> optionList = new ArrayList<Map.Entry<String, String>>(
				body.entrySet());

		RequestSpecBuilder builder = new RequestSpecBuilder();

		for (Map.Entry<String, String> option : optionList) {

			String param_key = option.getKey();

			String optionValue = option.getValue();

			if (paramType.equals("PathParameter")) {
				
				Reporter.log("<br>Path parameters: </b>"+param_key +" : "+optionValue);
				builder.addPathParameter(param_key, optionValue).build();
				
			} else if (paramType.equals("Multipart")) {
				
				Reporter.log("<br>Multipart parameter : "+param_key+" : "+optionValue);
				builder.addMultiPart(param_key, new File(optionValue)).build();
			}

		}
		RequestSpecification requestSpec = builder.build();
		return requestSpec;
	}

}

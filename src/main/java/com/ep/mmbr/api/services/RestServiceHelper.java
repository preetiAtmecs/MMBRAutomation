package com.ep.mmbr.api.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.specification.RequestSpecification;

public class RestServiceHelper {

	public RequestSpecification getRequestSpecification(String paramType,Object requestBody) {

		JSONObject body = (JSONObject) requestBody;
		List<Map.Entry<String, String>> optionList = new ArrayList<Map.Entry<String, String>>(
				body.entrySet());

		RequestSpecBuilder builder = new RequestSpecBuilder();

		for (Map.Entry<String, String> option : optionList) {

			String param_key = option.getKey();

			String optionValue = option.getValue();
			
			
			System.out.println("\n"+paramType+" :" + param_key + optionValue);	
			if(paramType.equals("PathParameter"))
			{
			
			builder.addPathParameter(param_key, optionValue).build();
			}
			else if (paramType.equals("Multipart")) 
			{
				
				builder.addMultiPart(param_key,  new File(optionValue)).build();
			}

		}
		RequestSpecification requestSpec = builder.build();
		return requestSpec;
	}

	
}

package com.ep.mmbr.api.services;

import static com.jayway.restassured.RestAssured.given;

import org.json.simple.JSONObject;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

/**
 * This class contains rest assured methods to send delete request and get response for
 * http delete method
 * 
 * @author preethi
 * 
 */
public class Delete implements Service {
	
	/**
	 * This method send delete request end point along with path parameters and returns the
	 * response
	 * 
	 * @param Request test data object (To send request and response from restassured)
	 * @param token(Authorization bearer /salesforce token)
	 * 
	 * @return rest assured response object 
	 */
	@Override
	public Response sendRequestAndgetResponse(JSONObject obj, String token) {
		
		
			RequestSpecification requestSpec = new RequestSpecificationBuilder()
			.getRequestSpecification("PathParameter", obj.get("requestParameters"));

	Response response = given().header("authorization", token).with()
			.spec(requestSpec).when().delete((String) obj.get("uri"));
	return response;

		
	}

	
	}


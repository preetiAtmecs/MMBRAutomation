package com.ep.mmbr.api.services;

import static com.jayway.restassured.RestAssured.given;
import groovy.json.JsonBuilder;

import org.json.simple.JSONObject;
import org.testng.Reporter;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

/**
 * This class contains rest assured methods to send put request and get response for
 * http put method
 * 
 * @author preethi
 * 
 */
public class Put implements Service {

	
	
	/**
	 * This method send put request end point along with path parameters and returns the
	 * response
	 * 
	 * @param Request test data object (To send request and response from restassured)
	 * @param token(Authorization bearer /salesforce token)
	 * 
	 * @return rest assured result response object 
	 */
	@Override
	public Response sendRequestAndgetResponse(JSONObject obj, String token) {
		Response response = null;

		RequestSpecification requestSpec = new RequestSpecificationBuilder()
				.getRequestSpecification(obj.get("contentType").toString(),
						obj.get("requestParameters"));

	
			JsonBuilder json = new JsonBuilder();
			json.setContent(obj.get("body"));
			Reporter.log("<br>Json body : " + json.toString());

			 response = given().header("authorization", token).with()
					.spec(requestSpec).and().contentType("application/json")
					.body(json.toString()).when().put((String) obj.get("uri"));
			return response;
		
	}

	
}

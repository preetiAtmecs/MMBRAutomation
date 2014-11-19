package com.ep.mmbr.api.services;

import static com.jayway.restassured.RestAssured.given;
import groovy.json.JsonBuilder;

import org.json.simple.JSONObject;
import org.testng.Reporter;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

/**
 * This class contains rest assured methods to send post request and get result response for
 * http post method
 * 
 * @author preethi
 * 
 */
public class Post implements Service {

	
	@Override
	public Response sendRequestAndgetResponse(JSONObject obj, String token) {
		Response response = null;

		RequestSpecification requestSpec = new RequestSpecificationBuilder()
				.getRequestSpecification(obj.get("contentType").toString(),
						obj.get("requestParameters"));

		if (obj.containsKey("body")) {

			response = postRequestWithBody((String) obj.get("uri"), token,
					requestSpec, obj.get("body"));
		}

		else {
			response = given().header("authorization", token).with()
					.spec(requestSpec).when().post((String) obj.get("uri"));
		}

		return response;
	}

	
	/**
	 * This method send post request with body and returns response result
	 * @param uri
	 * @param token
	 * @param requestSpec
	 * @param body
	 * @return
	 */
	
	public Response postRequestWithBody(String uri, String token,
			RequestSpecification requestSpec, Object body) {

		JsonBuilder json = new JsonBuilder();
		json.setContent(body);
		Reporter.log("<br>Json body : " + json.toString());

		Response response = given().header("authorization", token).with()
				.spec(requestSpec).and().contentType("application/json")
				.body(json.toString()).when().post(uri);
		return response;

	}
}

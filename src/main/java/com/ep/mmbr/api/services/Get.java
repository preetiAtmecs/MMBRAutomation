package com.ep.mmbr.api.services;

import static com.jayway.restassured.RestAssured.given;

import org.json.simple.JSONObject;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

/**
 * This class contains rest assured methods to send request and get response for
 * http get method
 * 
 * @author preethi
 * 
 */
public class Get implements Service {
	

	@Override
	public Response sendRequestAndgetResponse(JSONObject obj, String token) {
		Response response = null;
		if (obj.containsKey("requestParameters")) {

			response = sendRequestWithParameters(token,
					(String) obj.get("uri"), obj.get("requestParameters"));
		} else {

			response = sendRequest(token, (String) obj.get("uri"));
		}

		return response;
	}

	/**
	 * This method send get request with path parameters and returns the
	 * response
	 * 
	 * @param token(Authorization bearer /salesforce token)
	 * @param uri (End point uri to send request )
	 * @param pathparameters
	 * @return rest assured response object 
	 */
	public Response sendRequestWithParameters(String token, String uri,
			Object pathParameters) {

		RequestSpecification requestSpec = new RequestSpecificationBuilder()
				.getRequestSpecification("PathParameter", pathParameters);

		Response response = given().header("authorization", token).with()
				.spec(requestSpec).when().get(uri);
		return response;
	}

	/**
	 * This method send get request with out parameters and returns the response
	 * result
	 * 
	 * @param token(Authorization bearer /salesforce token)
	 * @param uri (End point uri to send request )
	 * @return rest assured response object 
	 */
	public Response sendRequest(String token, String uri) {
		Response response = given().header("authorization", token).when().get(uri);
		return response;
	}
}

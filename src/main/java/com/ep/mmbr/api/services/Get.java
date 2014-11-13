package com.ep.mmbr.api.services;

import static com.jayway.restassured.RestAssured.given;

import org.json.simple.JSONObject;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class Get implements Service {
	Response response = null;

	@Override
	public Response getResponse(JSONObject obj, String token) {

		System.out.println("\nSending GET Request........");

		if (obj.containsKey("request parameters")) {

			sendRequestWithParameters(token, (String) obj.get("uri"),
					obj.get("request parameters"));
		} else {

			sendRequest(token, (String) obj.get("uri"));
		}

		System.out.println("\nReceived Response .......... ");
		return response;
	}

	public void sendRequestWithParameters(String token, String uri,
			Object parameters) {

		RequestSpecification requestSpec = new RestServiceHelper()
				.getRequestSpecification("PathParameter",parameters);

		response = given().header("authorization", token).with()
				.spec(requestSpec).when().get(uri);
	}

	public void sendRequest(String token, String uri) {
		response = given().header("authorization", token).when().get(uri);
	}

}

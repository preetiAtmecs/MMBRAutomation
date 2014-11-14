package com.ep.mmbr.api.services;

import static com.jayway.restassured.RestAssured.given;

import org.json.simple.JSONObject;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class Post implements Service {

	@Override
	public Response getResponse(JSONObject obj, String token) {

		RequestSpecification requestSpec = new RestServiceHelper()
				.getRequestSpecification("Multipart",
						obj.get("requestParameters"));

		Response response = given().header("authorization", token).with()
				.spec(requestSpec).when().post((String) obj.get("uri"));

		return response;
	}
}

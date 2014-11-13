package com.ep.mmbr.api.services;

import static com.jayway.restassured.RestAssured.given;

import java.io.File;

import org.json.simple.JSONObject;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class Post implements Service {

	@Override
	public Response getResponse(JSONObject obj, String token) {

		System.out.println("\nSending POST Request........");
		
		RequestSpecification requestSpec = new RestServiceHelper()
		.getRequestSpecification("Multipart",obj.get("request parameters"));

		Response response = given().header("authorization", token).with()
				.spec(requestSpec).when().post((String) obj.get("uri"));

		System.out.println("\nReceived Response status code.......... "+response.asString());
		return response;
	}
}

/*System.out.println("\nSending POST Request........");

RequestSpecification requestSpec = new RestServiceHelper()
.getRequestSpecification("Multipart",obj.get("request body"));

Response response = given().header("authorization", token).with()
		.spec(requestSpec).when().post((String) obj.get("uri"));

System.out.println("\nReceived Response status code.......... "+response.asString());
return response;*/
package com.ep.mmbr.api.services;

import org.json.simple.JSONObject;

import com.jayway.restassured.response.Response;

public interface Service {

	public Response getResponse(JSONObject obj, String token);

}

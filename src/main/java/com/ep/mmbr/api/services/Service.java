package com.ep.mmbr.api.services;

import org.json.simple.JSONObject;

import com.jayway.restassured.response.Response;

/**
 * This interface can be implemented by
 * rest based services built on top of http builder classes to send and get response for the api service
 * @author Preethi
 *
 */

public interface Service {

	/**
	 * This method send  request end point with authorization bearer and returns the
	 * result response
	 * 
	 * @param Request test data object (To send request and response from restassured)
	 * @param token(Authorization bearer /salesforce token)
	 * 
	 * @return rest assured result response object 
	 */
	public Response sendRequestAndgetResponse(JSONObject obj, String token);

}

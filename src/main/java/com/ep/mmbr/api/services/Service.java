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
	 * Method to sent and get response of rest based http method like Get ,Post etc
	 * @param obj
	 * @param token
	 * @return
	 */
	public Response sendRequestAndgetResponse(JSONObject obj, String token);

}

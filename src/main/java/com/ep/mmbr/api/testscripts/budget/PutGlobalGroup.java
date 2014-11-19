package com.ep.mmbr.api.testscripts.budget;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.ep.mmbr.api.testscripts.TestSuiteBase;
import com.ep.mmbr.api.utilities.RequestDataBuilder;
import com.ep.mmbr.api.utilities.TestDataProvider;
import com.jayway.restassured.response.Response;

/**
 * This script validate response code for put valid global group with valid
 * budget id and global group id.
 * 
 * @author pg092111
 * 
 */
public class PutGlobalGroup extends TestSuiteBase {
	static String AuthorizationKey;
	static String budgetId;
	RequestDataBuilder requestDataBuilder = new RequestDataBuilder();
	TestDataProvider testDataProvider = new TestDataProvider();

	/**
	 * Upload budget and get budget id,set budget id to post global group
	 * request data ,Find all global groups and get recently posted group id.
	 * Set budget id and global group id in request data and send put request to rename the global group name
	 * Verify response code matches with 204
	 * @throws ParseException
	 */
	@Test
	public void testPostGlobalGroups() {

		JSONObject putGlobalGroupRequestData =testDataProvider
				.readFileData("budget", "putGlobalGroup.json");
		
		AuthorizationKey = CONFIG.getProperty("SalesforceToken");
		
		budgetId = requestDataBuilder
				.uploadBudgetAndGetBudgetID(AuthorizationKey);
		
		
		postGlobalGroup();
		
		Response getAllGroupsResponseBody = getAllGlobalGroupsResponse();
		
		JSONObject expectedDocumentDetails = requestDataBuilder
				.getDocumnetFromResponse(getAllGroupsResponseBody, "name",
						"Auto Test Group");
		
		String groupId = expectedDocumentDetails.get("id").toString();

				
		putGlobalGroupRequestData = requestDataBuilder.setValueInRequestData(
				putGlobalGroupRequestData, "requestParameters", "budget_id",
				budgetId);
		putGlobalGroupRequestData = requestDataBuilder.setValueInRequestData(
				putGlobalGroupRequestData, "body", "id", groupId);

		
		Reporter.log("<br><br>Sending pust reqest to rename the global group ");
		
		Response getResponse = requestDataBuilder.sendRequestAndGetResponse(
				putGlobalGroupRequestData, AuthorizationKey);

		Assert.assertTrue(requestDataBuilder.verifyResponseCode(getResponse,
				putGlobalGroupRequestData.get("status").toString()));
	}

	public void postGlobalGroup() {
		JSONObject postGlobalGroupRequestData = testDataProvider
				.readFileData("budget", "postGlobalGroup.json");

		postGlobalGroupRequestData = requestDataBuilder.setValueInRequestData(
				postGlobalGroupRequestData, "requestParameters", "budget_id",
				budgetId);

		Reporter.log("<br><br>Sending post reqest with new global group ");

		
		Reporter.log("<br><br>Sending request to post new global group");

		Response postGlobalGroupResponse = requestDataBuilder
				.sendRequestAndGetResponse(postGlobalGroupRequestData,
						AuthorizationKey);

		Assert.assertTrue(requestDataBuilder.verifyResponseCode(
				postGlobalGroupResponse,
				postGlobalGroupRequestData.get("status").toString()));
	}

	public Response getAllGlobalGroupsResponse() {
		JSONObject getAllGlobalGroupsRequestData = testDataProvider
				.readFileData("budget", "getAllGlobalGroups.json");

		requestDataBuilder.setValueInRequestData(getAllGlobalGroupsRequestData,
				"requestParameters", "budget_id", budgetId);

		Reporter.log("<br><br>Sending request to get all global groups by  budget id");

		Response getAllGroupsResponse = requestDataBuilder
				.sendRequestAndGetResponse(getAllGlobalGroupsRequestData,
						AuthorizationKey);

		return getAllGroupsResponse;
	}
}

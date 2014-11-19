package com.ep.mmbr.api.testscripts.budget;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.ep.mmbr.api.testscripts.TestSuiteBase;
import com.ep.mmbr.api.utilities.RequestDataBuilder;
import com.ep.mmbr.api.utilities.TestDataProvider;
import com.jayway.restassured.response.Response;

/**
 * This script validate response code for delete global group by valid budget id
 * and valid global group id request .
 * 
 * @author pg092111
 * 
 */
public class DeleteAGlobal extends TestSuiteBase {

	String budgetId;
	String globalGroupId;
	static String salesforceToken;
	RequestDataBuilder requestDataBuilder = new RequestDataBuilder();

	/**
	 * Upload budget and get budget id,set budget id and global group id to get
	 * global group request data Send request and verifies response code
	 * receives 200
	 */
	@Test
	public void testDeleteAGlobal() {
		salesforceToken = CONFIG.getProperty("SalesforceToken");
		String globalId = getGlobalIdFromGlobalGroup();

		JSONObject deleteAGlobalRequestData = new TestDataProvider().readFileData(
				"budget", "deleteAGlobal.json");

		deleteAGlobalRequestData = requestDataBuilder.setParameterValue(
				deleteAGlobalRequestData, "budget_id", budgetId);

		deleteAGlobalRequestData = requestDataBuilder.setParameterValue(
				deleteAGlobalRequestData, "global_group_id", globalGroupId);

		deleteAGlobalRequestData = requestDataBuilder.setParameterValue(
				deleteAGlobalRequestData, "global_id", globalId);
		
		Reporter.log("<br><br>Sending request to delete global  by budget id,global group id and global id");
		Response deleteAGlobalResponse = requestDataBuilder
				.sendRequestAndGetResponse(deleteAGlobalRequestData,
						salesforceToken);

		Assert.assertTrue(requestDataBuilder.verifyResponseCode(
				deleteAGlobalResponse, deleteAGlobalRequestData.get("status")
						.toString()));

	}

	public String getGlobalIdFromGlobalGroup() {
		JSONObject getGetAllGlobalsRequestData = new TestDataProvider()
				.readFileData("budget", "getAllGlobals.json");

		budgetId = requestDataBuilder
				.uploadBudgetAndGetBudgetID(salesforceToken);

		globalGroupId = requestDataBuilder.getGlobalGroupIdFromBudget(budgetId,
				salesforceToken);

		Reporter.log("<br><br>Set budget id and global id parametrs to request data : <br>Budgte ID:"
				+ budgetId + "<br>Global group id" + globalGroupId);

		getGetAllGlobalsRequestData = requestDataBuilder.setParameterValue(
				getGetAllGlobalsRequestData, "budget_id", budgetId);

		getGetAllGlobalsRequestData = requestDataBuilder.setParameterValue(
				getGetAllGlobalsRequestData, "global_group_id", globalGroupId);

		Reporter.log("<br><br>Sending reqest to get all globals by budget id and  global group id");
		Response getAllGlobalsResponse = requestDataBuilder
				.sendRequestAndGetResponse(getGetAllGlobalsRequestData,
						salesforceToken);

		String globalId = requestDataBuilder.getGlobalId(getAllGlobalsResponse);
		return globalId;
	}
}

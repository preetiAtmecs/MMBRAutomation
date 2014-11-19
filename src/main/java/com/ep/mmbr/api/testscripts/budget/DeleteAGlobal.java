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
 * This script validate result response code by sending request  to delete global group by valid budget id
 * and valid global group id  .
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
	 * Upload budget and get budget id and global group id,set id values to delete
	 * global group request data Send request . Verify the result response code
	 * receives 200
	 */
	@Test
	public void testDeleteAGlobal() {
		salesforceToken = CONFIG.getProperty("SalesforceToken");
		
		budgetId = requestDataBuilder
				.uploadBudgetAndGetBudgetID(salesforceToken);

		globalGroupId = requestDataBuilder.getGlobalGroupIdFromBudget(budgetId,
				salesforceToken);
		
		String globalId = getGlobalIdFromGlobalGroup();

		JSONObject deleteAGlobalRequestData = new TestDataProvider().readFileData(
				"budget", "deleteAGlobal.json");

		deleteAGlobalRequestData = requestDataBuilder.setValueInRequestData(
				deleteAGlobalRequestData,"requestParameters", "budget_id", budgetId);

		deleteAGlobalRequestData = requestDataBuilder.setValueInRequestData(
				deleteAGlobalRequestData,"requestParameters", "global_group_id", globalGroupId);

		deleteAGlobalRequestData =requestDataBuilder.setValueInRequestData(
				deleteAGlobalRequestData,"requestParameters", "global_id", globalId);
		
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

		

		Reporter.log("<br><br>Set budget id and global id parametrs to request data : <br>Budgte ID:"
				+ budgetId + "<br>Global group id" + globalGroupId);

		getGetAllGlobalsRequestData = requestDataBuilder.setValueInRequestData(
				getGetAllGlobalsRequestData,"requestParameters", "budget_id", budgetId);

		getGetAllGlobalsRequestData =requestDataBuilder.setValueInRequestData(
				getGetAllGlobalsRequestData,"requestParameters", "global_group_id", globalGroupId);

		Reporter.log("<br><br>Sending reqest to get all globals by budget id and  global group id");
		Response getAllGlobalsResponse = requestDataBuilder
				.sendRequestAndGetResponse(getGetAllGlobalsRequestData,
						salesforceToken);

		String globalId = requestDataBuilder.getGlobalId(getAllGlobalsResponse);
		return globalId;
	}
}

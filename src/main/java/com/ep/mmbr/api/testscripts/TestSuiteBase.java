/****
 * This is the TestSuite Base for mm.io build configuration.
 * This Class extends the Test Base Class
 * Class has Before & After Suite method to connect/Disconnect Database
 * Class has Before Suite method to set restassured base uri.
 * This is a must file for mmmb testNg script to execute & should not be deleted.
 */
package com.ep.mmbr.api.testscripts;

import java.io.FileInputStream;
import java.util.Properties;

import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.ep.mmbr.api.mongodb.MangoDBConnection;
import com.ep.mmbr.api.utilities.MMBRConstants;
import com.jayway.restassured.RestAssured;

public class TestSuiteBase {

	public static Properties CONFIG = null;
	public static boolean isInitalized = false;
	protected static MangoDBConnection dbConnection = new MangoDBConnection();

	@BeforeSuite
	public void dbConnect() throws Exception {
		
		initialize();
		
		RestAssured.useRelaxedHTTPSValidation();
		
		//dbConnection.getConnection(CONFIG.getProperty("MongoDB"));
		Reporter.log("\nBase uri: "+CONFIG.getProperty("BaseURI"));
		RestAssured.baseURI = CONFIG.getProperty("BaseURI");

	}

	@AfterSuite
	public void dbDisconnect() throws Exception {

	}

	public void initialize() throws Exception {
		
		if (!isInitalized) {
			CONFIG = new Properties();
			FileInputStream ip = new FileInputStream("."
					+ MMBRConstants.fileSeparator

					+ MMBRConstants.CONFIGPROPERTIES);
			System.out.println(ip);
			CONFIG.load(ip);

			isInitalized = true;
		}

	}

}
package com.ep.mmbr.api.utilities;

public class MMBRConstants {
	public static final String fileSeparator = System
			.getProperty("file.separator");
	public static final String CONFIGPROPERTIES = "config.properties";
	public static final String FILE_NOT_FOUND = "File not found:\n";
	public static final String RECIEVED_RESPONSE = "Received Response body:\n";
	public static final String EXECUTING_TESTCASE = "Executing test Case.......................\n";
	public static final String LOG = "report.log";
	public static final String TIME_ZONE = "IST";
	public static final String DATEFORMAT = "yyyy.mm.dd hh:mm:ss ";
	public static final String NOT_METHOD = " is not a HTTP method";
	public static final String TEST_FOLDER = "TestData";
	public static final String METHOD = "method";
	public static final String SUITE = "suite.json";

	public static final String PATH = System.getProperty("user.dir")
			+ System.getProperty("file.separator");

	private MMBRConstants() {
	}

}

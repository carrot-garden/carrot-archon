package com.carrotgarden.trader.util.quickfix;

public class FixSessionSettingsBuilder {

	// session id

	public String beginString = "";

	public String senderCompID = "";
	public String senderSubID = "";
	public String senderLocationID = "";

	public String targetCompID = "";
	public String targetSubID = "";
	public String targetLocationID = "";

	public String sessionQualifier = "";

	// session parameters

	public String dataDictionary = "";

	public String logPath = "./target/log";
	public String storePath = "./target/store";

	public String connectType = "";

	public String connectInterval = "3";
	public String heartbeatInterval = "15";

	public String connectHost = "";
	public String connectPort = "";

	public String socketUseSSL = "N";

	public String timeStart = "00:00:00";
	public String timeEnd = "23:59:59";

	// custom properties

	// tag 96
	public String senderPassword = "";

	// tag 553
	public String connectUsername = "";
	// tag 554
	public String connectPassword = "";

}

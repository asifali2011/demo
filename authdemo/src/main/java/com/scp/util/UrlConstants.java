package com.scp.util;

public interface UrlConstants {

	public static final String BASE_URL_NOTIFICATION = System.getProperty("notification-server");
//			Constants.serverCheck.equals("production")
//			? "http://10.10.20.50:8080"
//			: "http://10.10.7.5:8080";
	
	public static final String URL_NOTIFICATION = BASE_URL_NOTIFICATION + "/notification-service/notifications";

}

package com.scp.util;

import java.sql.Timestamp;
import java.util.*;

public class CommonUtil {

	static final String DATE_FORMAT = "MM/dd/yyyy";
	static final String TIMESTAMP_FORMAT = "MM/dd/yyyy HH:mm:ss";

	public static Timestamp getCurrentTimestamp() {
		Timestamp currentTimestamp = null;
		try {
			Calendar calendar = Calendar.getInstance();
			Date now = calendar.getTime();
			currentTimestamp = new Timestamp(now.getTime());
		} catch (Exception e) {
			Logger4j.getLogger().error("Exception:", e);
		}
		return currentTimestamp;
	}

	public static Throwable getRootCause(Throwable throwable) {
		if (throwable.getCause() != null)
			return getRootCause(throwable.getCause());

		return throwable;
	}
}

package com.scp.context;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppConfigContext {
	private static AnnotationConfigApplicationContext applicationContext = null;

	public static AnnotationConfigApplicationContext getAppConfigContext() {
		if (applicationContext == null) {
			applicationContext = new AnnotationConfigApplicationContext();
			applicationContext.scan("com.scp");
			applicationContext.refresh();
			return applicationContext;
		}

		return applicationContext;
	}
}

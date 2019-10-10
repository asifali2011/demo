package com.scp.bean;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class RestStructureBean {

	@NotNull(message = "required")
	private String name;

	@Valid
	@NotNull(message = "auth required")
	private SessionVerificationBean auth;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SessionVerificationBean getAuth() {
		return auth;
	}

	public void setAuth(SessionVerificationBean auth) {
		this.auth = auth;
	}

}

package com.scp.bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.scp.util.RegexConstant;

public class SessionVerificationBean {

	@NotNull(message = "token required")
	private String ssoToken;

	@NotNull(message = "session ID required")
	@Pattern(regexp = RegexConstant.UUID_PATTERN, message = "Please provide session ID")
	private String sessionId;

	@NotNull(message = "scp ID required")
	@Pattern(regexp = RegexConstant.UUID_PATTERN, message = "Please provide valid scp ID")
	private String scpId;

	public String getSsoToken() {
		return ssoToken;
	}

	public void setSsoToken(String ssoToken) {
		this.ssoToken = ssoToken;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getScpId() {
		return scpId;
	}

	public void setScpId(String scpId) {
		this.scpId = scpId;
	}

}

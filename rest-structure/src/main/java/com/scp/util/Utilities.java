package com.scp.util;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scp.bean.SessionVerificationBean;
import com.scp.sessionauth.entities.SessionAuth;
import com.scp.sessionauth.services.SessionAuthService;

@Service
public class Utilities {

	private SessionAuthService sessionAuthService;

	@Autowired
	public Utilities(SessionAuthService sessionAuthService) {
		this.sessionAuthService = sessionAuthService;

	}

	public SessionAuth checkUserSessionExistOrNot(SessionVerificationBean sessionVerificationBean) {
		try {
			if (sessionVerificationBean != null) {
				// for by pass the authentication
//				return sessionAuthService.getSessionByScpIdAndSessionId(
//						UUID.fromString(sessionVerificationBean.getScpId()),
//						UUID.fromString(sessionVerificationBean.getSessionId()));
//				for validate through openAm
				return sessionAuthService.getSessionAuth(UUID.fromString(sessionVerificationBean.getScpId()),
						UUID.fromString(sessionVerificationBean.getSessionId()), sessionVerificationBean.getSsoToken());
			}

		} catch (Exception e) {
			Logger4j.getLogger().error("Exception : ", e);
			return null;
		}
		return null;
	}

}

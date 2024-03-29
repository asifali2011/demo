package com.scp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.scp.util.Logger4j;

@Component
public class AccountAuthenticatoinProvider extends AbstractUserDetailsAuthenticationProvider {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken token)
			throws AuthenticationException {
		Logger4j.getLogger().debug("> additionalAuthenticationChecks");

		if (token.getCredentials() == null || userDetails.getPassword() == null) {
			throw new BadCredentialsException("Credentials may not be null.");
		}

		if (!passwordEncoder.matches((String) token.getCredentials(), userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid credentials.");
		}

		Logger4j.getLogger().debug("< additionalAuthenticationChecks");
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken token)
			throws AuthenticationException {
		Logger4j.getLogger().debug("> retrieveUser");

		UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		Logger4j.getLogger().debug("< retrieveUser");
		return userDetails;
	}

}

package com.scp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scp.entity.Account;
import com.scp.services.AccountService;
import com.scp.util.ApiResponse;

@RestController
public class AuthenticationController {

	@Autowired
	protected AuthenticationManager authenticationManager;

	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "/api/sample", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> sampleGet(@RequestParam(name = "username") String username) {
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setCode(HttpStatus.OK.value());
		apiResponse.setMessage("Success");
		apiResponse.setData(accountService.findByUsername(username));

		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Account> register(@Valid @RequestBody Account account) {

		Account createdAccount = accountService.createNewAccount(account);
		return new ResponseEntity<Account>(createdAccount, HttpStatus.CREATED);
	}

}

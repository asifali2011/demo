package com.scp.services;

import java.util.Collection;

import com.scp.entity.Account;

public interface AccountService {

	Collection<Account> findAll();

	Account findByUsername(String userename);

	Account createNewAccount(Account account);

}

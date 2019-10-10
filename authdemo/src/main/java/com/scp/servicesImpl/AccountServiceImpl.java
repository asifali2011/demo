package com.scp.servicesImpl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.scp.entity.Account;
import com.scp.entity.Role;
import com.scp.repository.AccountRepository;
import com.scp.services.AccountService;
import com.scp.services.RoleService;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private RoleService roleService;

	@Override
	public Collection<Account> findAll() {
		Collection<Account> accounts = accountRepository.findAll();
		return accounts;
	}

	@Override
	public Account findByUsername(String username) {
		Account account = accountRepository.findByUsername(username);
		return account;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public Account createNewAccount(Account account) {

		// Add the simple user role
		Role role = roleService.findByCode("ROLE_USER");
		Set<Role> roles = new HashSet<>();
		roles.add(role);

		// Validate the password
		if (account.getPassword().length() < 8) {
			throw new EntityExistsException("password should be greater than 8 characters");
		}

		// Encode the password
		account.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));

		// Create the role
		account.setRoles(roles);
		return accountRepository.save(account);
	}
}

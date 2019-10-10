package com.scp.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.scp.entity.Role;
import com.scp.repository.RoleRepository;
import com.scp.services.RoleService;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role findById(Long id) {
		Role role = roleRepository.getOne(id);
		return role;
	}

	@Override
	public Role findByCode(String code) {
		return roleRepository.findByCode(code);
	}
}

package com.scp.services;

import com.scp.entity.Role;

public interface RoleService {

    Role findById(Long id);

    Role findByCode(String code);

}

package com.mikovic.altma.services.implementations;

import com.mikovic.altma.modeles.Role;
import com.mikovic.altma.repositories.RoleRepository;
import com.mikovic.altma.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

}

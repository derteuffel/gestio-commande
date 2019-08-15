package com.derteuffel.services;

import com.derteuffel.entities.Role;
import com.derteuffel.repositories.RoleRepository;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;


    public Role findOne(int roleId){
        return roleRepository.getOne(roleId);
    }

    public List<Role> findAll(){
        return roleRepository.findAll();
    }

    public Role findByRole(String role){
        return roleRepository.findByRole(role);
    }
}

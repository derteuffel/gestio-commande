package com.derteuffel.services;

import com.derteuffel.entities.Role;
import com.derteuffel.entities.User;
import com.derteuffel.repositories.RoleRepository;
import com.derteuffel.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Max;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("userService")
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }



    public User findOne(int id){
        return userRepository.getOne(id);
    }
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserByEmailOrName(String email, String name){
        return userRepository.findByNameOrEmail(email,name);
    }

    public void saveUser(User user) {
        List<User> users=userRepository.findAll();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        if (users.size()<=2){
            Role userRole = roleRepository.findByRole("ROOT_MASTER");
            if (userRole == null){
                Role role=new Role();
                role.setRole("ROOT_MASTER");
                roleRepository.save(role);
                user.setRoles(new HashSet<Role>(Arrays.asList(role)));
            }else {
                user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
            }
        }else {
            Role userRole = roleRepository.findByRole("SECRETARIAT");
            if (userRole ==  null){
                Role role= new Role();
                role.setRole("SECRETARIAT");
                roleRepository.save(role);
                user.setRoles(new HashSet<Role>(Arrays.asList(role)));
            }else {
                user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
            }
        }
        userRepository.save(user);
    }

    public void updateUser(User user){
        userRepository.save(user);
    }

    public List<User> all(){
        return userRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
    }
}

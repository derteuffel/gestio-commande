package com.derteuffel.gestioncommande.Services;

import com.derteuffel.gestioncommande.entities.User;
import com.derteuffel.gestioncommande.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getOne(Long userId) {
        return userRepository.getOne(userId);
    }

    public <S extends User> S save(S s) {
        return userRepository.save(s);
    }

    public long count() {
        return userRepository.count();
    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }
}
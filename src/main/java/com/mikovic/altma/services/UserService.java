package com.mikovic.altma.services;


import com.mikovic.altma.modeles.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    void deleteByEmail(String email);
    void deleteById(Long id);
    void delete(User user);
    boolean existsByEmail(String email);
    User save (User user);
    User findOneById(Long id);
    List<User> findAll();
    List<User> findByFirstNameAndLastName(String firstName, String lastName);
    User findByNickName(String nickName);
    User findByEmail(String email);
    User findByProviderId(String providerId);
    User findByEmailIgnoreCase(String email);



}

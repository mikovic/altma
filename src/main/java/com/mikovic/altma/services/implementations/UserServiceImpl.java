package com.mikovic.altma.services.implementations;

import com.mikovic.altma.modeles.Role;
import com.mikovic.altma.modeles.User;
import com.mikovic.altma.repositories.UserRepository;
import com.mikovic.altma.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(@Autowired UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        if (user == null)
            return null;
        else return userRepository.save(user);
    }

    @Override
    public User findOneById(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findByFirstNameAndLastName(String firstName, String lastName) {
        if (firstName == null || lastName == null)
            return null;
        else return userRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public User findByNickName(String nickName) {
        if (nickName == null)
            return null;
        else return (userRepository.findByNickName(nickName).isPresent()) ?
                userRepository.findByNickName(nickName).get(): null;    }



    @Override
    public User findByEmail(String email) {
        if (email == null)
            return null;
        else  return (userRepository.findByEmail(email).isPresent()) ?
                userRepository.findByEmail(email).get() : null;

    }

    @Override
    public User findByProviderId(String providerId)
    {
        if (providerId == null)
            return null;
        else return (providerId != null && userRepository.findByProviderId(providerId).isPresent()) ?
                userRepository.findByProviderId(providerId).get() : null;

    }

    @Override
    public User findByEmailIgnoreCase(String email) {
        if (email == null)
            return null;
        else  return (userRepository.findByEmailIgnoreCase(email).isPresent()) ?
                userRepository.findByEmail(email).get() : null;

    }


    @Override
    public UserDetails loadUserByUsername(String userName)  throws UsernameNotFoundException {
//        Compare the password the user provided with the user’s password from the database.
        User user = null;
        if (userName.contains("@")) {
            user = findByEmail(userName);

        } else {
            user = findByNickName(userName);
        }

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getNickName(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}

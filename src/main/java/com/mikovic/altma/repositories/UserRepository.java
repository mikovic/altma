package com.mikovic.altma.repositories;

import com.mikovic.altma.modeles.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    // методы save и deleteById есть в CrudRepository по умолчанию

    void deleteByEmail(String email);
    boolean existsByEmail(String email);
    @Query("SELECT user From User user left join fetch user.roles  WHERE user.email =:email")
    Optional<User> findByEmail(String email);
    @Query("SELECT user FROM User user left join fetch user.roles WHERE user.id =:id")
    Optional<User> findById(Long id);
    @Query("SELECT user FROM User user left join fetch user.roles WHERE user.providerId =:providerId")
    Optional<User> findByProviderId(String providerId);

    List<User> findByFirstNameAndLastName(String firstName, String lastName);

    Optional<User> findByNickName(String nickName);
    Optional<User>  findByEmailIgnoreCase(String email);

}

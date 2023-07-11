package com.mikovic.altma.services;

import com.mikovic.altma.modeles.ConfirmationToken;
import com.mikovic.altma.modeles.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository("ConfirmationTokenRepository")
@Transactional
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
    @Modifying
    @Query("DELETE FROM ConfirmationToken confirmationToken WHERE confirmationToken.user.id =:userId")
    void deleteConfirmationTokenFromUser(@Param("userId") Long userId);
}

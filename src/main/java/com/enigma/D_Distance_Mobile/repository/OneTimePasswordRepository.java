package com.enigma.D_Distance_Mobile.repository;

import com.enigma.D_Distance_Mobile.entity.OneTimePassword;
import com.enigma.D_Distance_Mobile.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OneTimePasswordRepository extends JpaRepository<OneTimePassword, String> {
    Optional<OneTimePassword> findOneTimePasswordByToken(String token);
    List<OneTimePassword> findByExpiryDateBefore(Date date);

}

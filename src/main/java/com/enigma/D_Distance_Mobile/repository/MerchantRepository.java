package com.enigma.D_Distance_Mobile.repository;

import com.enigma.D_Distance_Mobile.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, String> {
}

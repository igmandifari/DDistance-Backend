package com.enigma.D_Distance_Mobile.repository;

import com.enigma.D_Distance_Mobile.entity.CreditAnalyst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditAnalystRepository extends JpaRepository<CreditAnalyst,String> {
}

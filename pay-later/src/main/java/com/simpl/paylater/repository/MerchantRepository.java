package com.simpl.paylater.repository;

import com.simpl.paylater.enitity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Integer> {

    Merchant findMerchantByName(String name);
}

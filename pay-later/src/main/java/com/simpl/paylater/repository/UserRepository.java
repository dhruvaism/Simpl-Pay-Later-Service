package com.simpl.paylater.repository;

import com.simpl.paylater.enitity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT * FROM User as u where u.credit_limit = u.dues", nativeQuery = true)
    List<User> findAllByDuesGreaterThanEqualToCreditLimit();
}

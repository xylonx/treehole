package com.xr.treehole.repositories;


import com.xr.treehole.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM user where email_address = ?1", nativeQuery = true)
    User findByEmailAddress(String emailAddress);

}

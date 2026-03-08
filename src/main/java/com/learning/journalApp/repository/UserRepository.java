package com.learning.journalApp.repository;

import com.learning.journalApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    User findByUserName(String userName);

    void deleteByUserName(String userName);
}

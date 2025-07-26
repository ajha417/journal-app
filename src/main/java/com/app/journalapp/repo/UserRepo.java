package com.app.journalapp.repo;

import com.app.journalapp.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

    void deleteByUsername(String username);

}

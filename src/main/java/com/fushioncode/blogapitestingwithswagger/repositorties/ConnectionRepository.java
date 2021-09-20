package com.fushioncode.blogapitestingwithswagger.repositorties;

import com.fushioncode.blogapitestingwithswagger.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    Optional<Connection> findByBlogger_Id(Long bloggerId);
}

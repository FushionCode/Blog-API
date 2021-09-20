package com.fushioncode.blogapitestingwithswagger.repositorties;

import com.fushioncode.blogapitestingwithswagger.entity.Blogger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BloggerRepository extends JpaRepository<Blogger, Long> {
    Optional<Blogger> findByUserNameAndPassword(String userName, String password);
    Optional<Blogger> findByUserName(String userName);
}

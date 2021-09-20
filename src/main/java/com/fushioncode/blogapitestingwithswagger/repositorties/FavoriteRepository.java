package com.fushioncode.blogapitestingwithswagger.repositorties;

import com.fushioncode.blogapitestingwithswagger.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByBlogger_Id(Long bloggerId);
}

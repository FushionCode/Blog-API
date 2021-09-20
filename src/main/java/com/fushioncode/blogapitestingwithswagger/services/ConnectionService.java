package com.fushioncode.blogapitestingwithswagger.services;

import com.fushioncode.blogapitestingwithswagger.dto.BloggerDto;
import com.fushioncode.blogapitestingwithswagger.entity.Blogger;
import com.fushioncode.blogapitestingwithswagger.entity.Connection;

import java.util.List;

public interface ConnectionService {
    void saveConnection(Long bloggerId, Long connectionId);
    List<BloggerDto> viewMyConnections(Long userId);

}

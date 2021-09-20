package com.fushioncode.blogapitestingwithswagger.services;

import com.fushioncode.blogapitestingwithswagger.dto.BloggerDto;
import com.fushioncode.blogapitestingwithswagger.entity.Blogger;

import javax.servlet.http.HttpSession;

public interface BloggerService {
    Blogger createAccount(Blogger blogger);
    BloggerDto getBlogger(Blogger blogger, HttpSession httpSession) throws Exception;
    void deActivateAccount(Long bloggerId);
    void cancelDeactivation(Long bloggerId);
}

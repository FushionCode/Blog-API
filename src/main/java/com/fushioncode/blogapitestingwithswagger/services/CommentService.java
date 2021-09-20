package com.fushioncode.blogapitestingwithswagger.services;

import com.fushioncode.blogapitestingwithswagger.entity.Comment;

import javax.servlet.http.HttpSession;

public interface CommentService {
    Comment makeComment(Comment comment, HttpSession httpSession);
}

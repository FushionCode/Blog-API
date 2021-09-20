package com.fushioncode.blogapitestingwithswagger.controller;

import com.fushioncode.blogapitestingwithswagger.entity.Comment;
import com.fushioncode.blogapitestingwithswagger.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("blogapi/blogger/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/make-comment")
    public Comment saveComment(@RequestBody Comment comment, HttpSession httpSession){
        return commentService.makeComment(comment, httpSession);
    }

}

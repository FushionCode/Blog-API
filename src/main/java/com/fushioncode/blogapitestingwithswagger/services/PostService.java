package com.fushioncode.blogapitestingwithswagger.services;

import com.fushioncode.blogapitestingwithswagger.entity.Blogger;
import com.fushioncode.blogapitestingwithswagger.entity.Post;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface PostService {
    Post createPost(Post post, HttpSession httpSession);
    List<Post> viewPost(Blogger blogger, HttpSession httpSession);
    Post likePost(Long postId, Long bloggerId);
    Post dislikePOst(Long postId, Long bloggerId);
}

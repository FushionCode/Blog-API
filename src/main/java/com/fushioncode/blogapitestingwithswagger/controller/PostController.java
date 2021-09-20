package com.fushioncode.blogapitestingwithswagger.controller;

import com.fushioncode.blogapitestingwithswagger.entity.Blogger;
import com.fushioncode.blogapitestingwithswagger.entity.Post;
import com.fushioncode.blogapitestingwithswagger.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping ("blogapi/blogger/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public Post createPost(@RequestBody Post post, HttpSession httpSession){
        return postService.createPost(post, httpSession);
    }

    @GetMapping("/list")
    public List<Post> viewOtherBloggersPost(@RequestBody Blogger blogger, HttpSession httpSession){
        return postService.viewPost(blogger, httpSession);
    }

    @PutMapping("/like")
    public Post likePost(@RequestParam Long postId, @RequestParam Long bloggerId){
        return postService.likePost(postId, bloggerId);
    }

    @PutMapping("/dislike")
    public Post dislikePost(@RequestParam Long postId, @RequestParam Long bloggerId){
        return postService.dislikePOst(postId, bloggerId);
    }
}

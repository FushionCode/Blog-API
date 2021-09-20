package com.fushioncode.blogapitestingwithswagger.services.Impl;

import com.fushioncode.blogapitestingwithswagger.entity.Blogger;
import com.fushioncode.blogapitestingwithswagger.entity.Comment;
import com.fushioncode.blogapitestingwithswagger.entity.Post;
import com.fushioncode.blogapitestingwithswagger.exception.NotLoggedInException;
import com.fushioncode.blogapitestingwithswagger.repositorties.BloggerRepository;
import com.fushioncode.blogapitestingwithswagger.repositorties.CommentRepository;
import com.fushioncode.blogapitestingwithswagger.repositorties.PostRepository;
import com.fushioncode.blogapitestingwithswagger.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BloggerRepository bloggerRepository;
    @Autowired
    private PostRepository postRepository;

    @Override
    public Comment makeComment(Comment comment, HttpSession httpSession) {
        System.out.println("I am making this comment with username: "+comment);
        Optional<Blogger> getBlogger = bloggerRepository.findByUserName(comment.getBlogger().getUserName());
        Blogger blogger = new Blogger();
        if (getBlogger.isPresent()) blogger = getBlogger.get();
        System.out.println(blogger);

        Blogger loggedBlogger =  (Blogger) httpSession.getAttribute(blogger.getUserName());

        if (loggedBlogger != null){
            Optional<Post> findPost = postRepository.findById(comment.getPost().getId());
            Post post = new Post();
            if (findPost.isPresent()) post = findPost.get();

            comment.setBlogger(blogger);
            comment.setPost(post);

            return commentRepository.save(comment);
        }
        throw new NotLoggedInException("User NOT LOGGED IN");
    }
}

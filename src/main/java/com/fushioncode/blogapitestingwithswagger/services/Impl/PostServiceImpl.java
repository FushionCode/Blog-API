package com.fushioncode.blogapitestingwithswagger.services.Impl;

import com.fushioncode.blogapitestingwithswagger.entity.Blogger;
import com.fushioncode.blogapitestingwithswagger.entity.Post;
import com.fushioncode.blogapitestingwithswagger.exception.NotLoggedInException;
import com.fushioncode.blogapitestingwithswagger.exception.PostNotFoundException;
import com.fushioncode.blogapitestingwithswagger.exception.UserNotFoundException;
import com.fushioncode.blogapitestingwithswagger.repositorties.BloggerRepository;
import com.fushioncode.blogapitestingwithswagger.repositorties.PostRepository;
import com.fushioncode.blogapitestingwithswagger.services.PostService;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private BloggerRepository bloggerRepository;

    @Override
    public Post createPost(Post post, HttpSession httpSession) {
        System.out.println(post.getBlogger().getUserName());
        Blogger loggedBlogger = (Blogger) httpSession.getAttribute(post.getBlogger().getUserName());
        System.out.println(loggedBlogger);
        Post newPost = new Post();
        if (loggedBlogger != null){
            Optional<Blogger> loggedUser = bloggerRepository.findByUserName(loggedBlogger.getUserName());
            Blogger blogger = new Blogger();

            if (loggedUser.isPresent()) blogger = loggedUser.get();

            newPost.setTitle(post.getTitle());
            newPost.setPostContent(post.getPostContent());
            newPost.setBlogger(blogger);

            return postRepository.save(newPost);
        }
        throw new NotLoggedInException("User not loggedIn");
    }

    @Override
    public List<Post> viewPost(Blogger blogger, HttpSession httpSession) {
        System.out.println("User with this username is trying to view post: "+ blogger);
        Blogger sessionBlogger = (Blogger) httpSession.getAttribute(blogger.getUserName());
        System.out.println("This user is trying to see all other user post: "+sessionBlogger);
        List<Post> postList = new ArrayList<>();

        Optional<Blogger> getLoggedBlogger = bloggerRepository.findByUserName(sessionBlogger.getUserName());
        Blogger blogger_ = new Blogger();

        if (getLoggedBlogger.isPresent()) blogger_ = getLoggedBlogger.get();
        List<Post> posts = postRepository.findAll();
        for (Post post: posts){
            if (!post.getBlogger().getId().equals(blogger_.getId())){
                postList.add(post);
            }
        }
        return postList;
    }

    @Override
    public Post likePost(Long postId, Long bloggerId) {
        Optional<Post> findPost = postRepository.findById(postId);
        Post post = new Post();
        if (findPost.isPresent())
            post = findPost.get();
        else
            throw new PostNotFoundException("Post Not Available");

        Optional<Blogger> findBlogger = bloggerRepository.findById(bloggerId);
        Blogger blogger = new Blogger();
        if (findBlogger.isPresent()) {
            blogger = findBlogger.get();

            Set<Blogger> likers = post.getLikes();
            Set<Blogger> dislikers = post.getDislike();

            if (dislikers.contains(blogger)) {
                dislikers.remove(blogger);
            }
            likers.add(blogger);
            post.setLikes(likers);
            post.setDislike(dislikers);

            return postRepository.save(post);

        }
        else
            throw new UserNotFoundException("Unrecognized user");
    }

    @Override
    public Post dislikePOst(Long postId, Long bloggerId) {
        Optional<Post> findPost = postRepository.findById(postId);
        Post post = new Post();
        if (findPost.isPresent())
            post = findPost.get();
        else
            throw new PostNotFoundException("Post Not Available");

        Optional<Blogger> findBlogger = bloggerRepository.findById(bloggerId);
        Blogger blogger = new Blogger();
        if (findBlogger.isPresent()) {
            blogger = findBlogger.get();

            Set<Blogger> likers = post.getLikes();
            Set<Blogger> dislikers = post.getDislike();

            if (likers.contains(blogger)) {
                likers.remove(blogger);
            }
            dislikers.add(blogger);
            post.setLikes(likers);
            post.setDislike(dislikers);

            return postRepository.save(post);

        }
        else
            throw new UserNotFoundException("Unrecognized user");
    }


}

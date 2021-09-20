package com.fushioncode.blogapitestingwithswagger.services.Impl;

import com.fushioncode.blogapitestingwithswagger.entity.Favorite;
import com.fushioncode.blogapitestingwithswagger.entity.Blogger;
import com.fushioncode.blogapitestingwithswagger.entity.Post;
import com.fushioncode.blogapitestingwithswagger.exception.NoFavoritePostException;
import com.fushioncode.blogapitestingwithswagger.exception.NotLoggedInException;
import com.fushioncode.blogapitestingwithswagger.exception.PostNotFoundException;
import com.fushioncode.blogapitestingwithswagger.exception.UserNotFoundException;
import com.fushioncode.blogapitestingwithswagger.repositorties.BloggerRepository;
import com.fushioncode.blogapitestingwithswagger.repositorties.FavoriteRepository;
import com.fushioncode.blogapitestingwithswagger.repositorties.PostRepository;
import com.fushioncode.blogapitestingwithswagger.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private BloggerRepository bloggerRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;




    @Override
    public Favorite addMyFavoritePosts(long blog_blogger, Long postId, HttpSession httpSession) {
        Optional<Blogger> findBlogger = bloggerRepository.findById(blog_blogger);
        Blogger blogger = new Blogger();
        if (findBlogger.isPresent())
            blogger = findBlogger.get();
        else
            throw new UserNotFoundException("User NOT FOUND");

        Optional<Post> findPost = postRepository.findById(postId);
        Post newFavPost = new Post();
        if (findPost.isPresent())
            newFavPost = findPost.get();
        else
            throw new PostNotFoundException("Post not found");

        Blogger loggedBlogger = (Blogger) httpSession.getAttribute(findBlogger.get().getUserName());
        if (loggedBlogger != null) {
            Optional<Favorite> favorite = favoriteRepository.findByBlogger_Id(blogger.getId());

            if (favorite.isPresent()) {
                Set<Post> myFavPosts = favorite.get().getMyFavoritePosts();
                myFavPosts.add(newFavPost);
                favorite.get().setMyFavoritePosts(myFavPosts);
                return favoriteRepository.save(favorite.get());
            }
            else {
                Favorite myFavorite = new Favorite();
                Set<Post> myFavPost = new HashSet<>();
                myFavPost.add(newFavPost);

                myFavorite.setBlogger(blogger);
                myFavorite.setMyFavoritePosts(myFavPost);

                return favoriteRepository.save(myFavorite);
            }
        }
        else{
            throw new NotLoggedInException("User not logged in");
        }
    }

    @Override
    public List<Post> viewAllMyFavorite(Blogger blogger, HttpSession httpSession) {
        Blogger loggedBlogger = (Blogger) httpSession.getAttribute(blogger.getUserName());

        Optional<Blogger> findBlogger = bloggerRepository.findByUserName(blogger.getUserName());
        Blogger foundBlogger = new Blogger();
        if (findBlogger.isPresent())
            foundBlogger = findBlogger.get();
        else
            throw new UserNotFoundException("User NOT FOUND");


        if (loggedBlogger != null){
            Optional<Favorite> myFav = favoriteRepository.findByBlogger_Id(foundBlogger.getId());
            if (myFav.isPresent()){
                return new ArrayList<>(myFav.get().getMyFavoritePosts());
            }
            else{
                throw new NoFavoritePostException("This user has no favorite post");
            }

        }
        else{
            throw new NotLoggedInException("User NOT LOGGED IN");
        }
    }
}

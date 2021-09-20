package com.fushioncode.blogapitestingwithswagger.services;

import com.fushioncode.blogapitestingwithswagger.entity.Blogger;
import com.fushioncode.blogapitestingwithswagger.entity.Favorite;
import com.fushioncode.blogapitestingwithswagger.entity.Post;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface FavoriteService {
    Favorite addMyFavoritePosts(long bloggerId, Long postId, HttpSession httpSession);
    List<Post> viewAllMyFavorite(Blogger blogger, HttpSession httpSession);
}

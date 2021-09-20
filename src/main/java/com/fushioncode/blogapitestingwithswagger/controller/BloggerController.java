package com.fushioncode.blogapitestingwithswagger.controller;

import com.fushioncode.blogapitestingwithswagger.dto.BloggerDto;
import com.fushioncode.blogapitestingwithswagger.entity.Blogger;
import com.fushioncode.blogapitestingwithswagger.entity.Favorite;
import com.fushioncode.blogapitestingwithswagger.entity.Post;
import com.fushioncode.blogapitestingwithswagger.services.BloggerService;
import com.fushioncode.blogapitestingwithswagger.services.ConnectionService;
import com.fushioncode.blogapitestingwithswagger.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("blogapi/blogger")
public class BloggerController {


    private BloggerService bloggerService;
    private FavoriteService favoriteService;
    private ConnectionService connectionService;

    @Autowired
    public BloggerController(BloggerService bloggerService, FavoriteService favoriteService, ConnectionService connectionService) {
        this.bloggerService = bloggerService;
        this.favoriteService = favoriteService;
        this.connectionService = connectionService;
    }

    @PostMapping
    public void createNewBlogger(@RequestBody Blogger blogger){
        bloggerService.createAccount(blogger);
    }

    @PostMapping("/login")
    public BloggerDto loginUser(@RequestBody Blogger blogger, HttpSession httpSession) throws Exception {
        return bloggerService.getBlogger(blogger, httpSession);
    }

    @PostMapping("/favorite/add/{postId}")
    public Favorite addPostToFavorite(@RequestParam long bloggerId, @PathVariable long postId , HttpSession httpSession){
        return favoriteService.addMyFavoritePosts(bloggerId, postId, httpSession);
    }

    @DeleteMapping("/favorite/delete")
    public ResponseEntity<String> deletePostFromFavorite(){
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/favorite")
    public List<Post> viewMyFavoritePost(@RequestBody Blogger blogger, HttpSession httpSession){
        return favoriteService.viewAllMyFavorite(blogger, httpSession);
    }

    @PostMapping("connection/save/{newConnectId}")
    public void createConnectionWithOtherBloggers(@RequestParam Long bloggerId, @PathVariable Long newConnectId){
        connectionService.saveConnection(bloggerId, newConnectId);
    }

    @GetMapping("/connection")
    public List<BloggerDto> viewMyConnections(@RequestParam Long bloggerId){
        return connectionService.viewMyConnections(bloggerId);
    }

    @PutMapping("/de_activate/{bloggerId}")
    public void deActivateAccount(@PathVariable Long bloggerId){
        bloggerService.deActivateAccount(bloggerId);
    }

    @PutMapping("/cancel_deactivation/{bloggerId}")
    public void cancelDeactivation(@PathVariable Long bloggerId){
        bloggerService.cancelDeactivation(bloggerId);
    }
}

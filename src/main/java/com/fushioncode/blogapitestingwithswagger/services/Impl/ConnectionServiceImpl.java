package com.fushioncode.blogapitestingwithswagger.services.Impl;

import com.fushioncode.blogapitestingwithswagger.dto.BloggerDto;
import com.fushioncode.blogapitestingwithswagger.entity.Blogger;
import com.fushioncode.blogapitestingwithswagger.entity.Connection;
import com.fushioncode.blogapitestingwithswagger.exception.NoConnectionsException;
import com.fushioncode.blogapitestingwithswagger.exception.UserNotFoundException;
import com.fushioncode.blogapitestingwithswagger.repositorties.BloggerRepository;
import com.fushioncode.blogapitestingwithswagger.repositorties.ConnectionRepository;
import com.fushioncode.blogapitestingwithswagger.services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ConnectionServiceImpl implements ConnectionService {

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private BloggerRepository bloggerRepository;

    @Override
    public void saveConnection(Long bloggerId, Long connectionId) {
        Optional<Blogger> findBlogger = bloggerRepository.findById(bloggerId);
        Blogger blogger = new Blogger();
        if (findBlogger.isPresent())
            blogger = findBlogger.get();
        else
            throw new UserNotFoundException("User NOT FOUND");

        Optional<Blogger> myNewConnection = bloggerRepository.findById(connectionId);
        Blogger newConnect = new Blogger();
        if (myNewConnection.isPresent())
            if (!myNewConnection.get().equals(blogger))
                newConnect = myNewConnection.get();
        else
            throw new UserNotFoundException("New connected user not found");

        Optional<Connection> findMyConnection = connectionRepository.findByBlogger_Id(blogger.getId());
        if (findMyConnection.isPresent()){
            Set<Blogger> myConnectionList = findMyConnection.get().getMyConnections();
            myConnectionList.add(newConnect);
            findMyConnection.get().setMyConnections(myConnectionList);

            connectionRepository.save(findMyConnection.get());
        }
        else {
            Connection connection = new Connection();
            Set<Blogger> connectionList = new HashSet<>();
            connectionList.add(newConnect);
            connection.setBlogger(blogger);
            connection.setMyConnections(connectionList);

            connectionRepository.save(connection);
        }

    }

    @Override
    public List<BloggerDto> viewMyConnections(Long userId) {
        Optional<Blogger> findBlogger = bloggerRepository.findById(userId);
        Blogger blogger = new Blogger();
        if (findBlogger.isPresent())
            blogger = findBlogger.get();
        else
            throw new UserNotFoundException("User not found");

        Optional<Connection> findMyConnectList = connectionRepository.findByBlogger_Id(blogger.getId());
        if (findMyConnectList.isPresent()){
            List<BloggerDto> myConnects = new ArrayList<>();
            for (Blogger connectedBlogger: findMyConnectList.get().getMyConnections()){
                BloggerDto bloggerDto = new BloggerDto();
                bloggerDto.setFirstName(connectedBlogger.getFirstName());
                bloggerDto.setLastName(connectedBlogger.getLastName());
                bloggerDto.setEmail(connectedBlogger.getEmail());
                bloggerDto.setUserName(connectedBlogger.getUserName());

                myConnects.add(bloggerDto);
            }
            return myConnects;
        }
        else {
            throw new NoConnectionsException("There no connections");
        }
    }
}

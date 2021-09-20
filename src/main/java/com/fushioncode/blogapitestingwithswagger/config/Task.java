package com.fushioncode.blogapitestingwithswagger.config;

import com.fushioncode.blogapitestingwithswagger.entity.Blogger;
import com.fushioncode.blogapitestingwithswagger.repositorties.BloggerRepository;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;

public class Task implements Callable<String> {
    private final String name;
    private final Blogger blogger;
    private final BloggerRepository bloggerRepository;


    public Task(String name, Blogger blogger, BloggerRepository bloggerRepository) {
        this.name = name;
        this.blogger = blogger;
        this.bloggerRepository = bloggerRepository;
    }

    @Override
    public String call() throws Exception {
        assert blogger != null;
        blogger.setStatus("Inactive");
        bloggerRepository.save(blogger);
        System.out.println("Task ["+name+"] executed on: "+ LocalDateTime.now().toString().replace("T00:", " "));
        return "Task [" + name + "] is Successful";
    }
}

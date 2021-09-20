package com.fushioncode.blogapitestingwithswagger.services.Impl;

import com.fushioncode.blogapitestingwithswagger.config.Task;
import com.fushioncode.blogapitestingwithswagger.dto.BloggerDto;
import com.fushioncode.blogapitestingwithswagger.entity.Blogger;
import com.fushioncode.blogapitestingwithswagger.exception.UserNotFoundException;
import net.bytebuddy.asm.Advice;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fushioncode.blogapitestingwithswagger.repositorties.BloggerRepository;
import com.fushioncode.blogapitestingwithswagger.services.BloggerService;

import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class   BloggerServiceImpl implements BloggerService {

    //private volatile Boolean deActivate;

    private Thread t;
    private ScheduledExecutorService executorService;
    private ScheduledFuture<String> deActivateSchedule;
    @Autowired
    private BloggerRepository bloggerRepository;

    @Override
    public Blogger createAccount(Blogger blogger) {
        bloggerRepository.save(blogger);
        return blogger;
    }

    @Override
    public BloggerDto getBlogger(Blogger blogger, HttpSession httpSession) throws Exception {
        Blogger loggedInBlogger = (Blogger) httpSession.getAttribute(blogger.getUserName());
        BloggerDto bloggerDto = new BloggerDto();
        System.out.println(loggedInBlogger  );
        if(loggedInBlogger == null){
            Optional<Blogger> fetchedBlogger = bloggerRepository.findByUserNameAndPassword(blogger.getUserName(), blogger.getPassword());
            System.out.println("This is to check the logging information: " +blogger);
            Blogger currentBlogger;
            if(fetchedBlogger.isPresent() && fetchedBlogger.get().getStatus().equals("active")){
                currentBlogger = fetchedBlogger.get();
                httpSession.setAttribute(blogger.getUserName(), currentBlogger);
                bloggerDto.setFirstName(currentBlogger.getFirstName());
                bloggerDto.setLastName(currentBlogger.getLastName());
                bloggerDto.setEmail(currentBlogger.getEmail());
                bloggerDto.setUserName(currentBlogger.getUserName());
                return bloggerDto;
            }else
                throw new Exception("Account not found");
        }
        bloggerDto.setUserName(loggedInBlogger.getUserName());
        bloggerDto.setEmail(loggedInBlogger.getEmail());
        bloggerDto.setFirstName(loggedInBlogger.getUserName());
        bloggerDto.setLastName(loggedInBlogger.getLastName());

        return bloggerDto;
    }

    @Override
    public void deActivateAccount(Long bloggerId) {
        Optional<Blogger> findBlogger = bloggerRepository.findById(bloggerId);
        Blogger blogger = new Blogger();
        if (findBlogger.isPresent())
            blogger = findBlogger.get();
        else
            throw new UserNotFoundException("User not found");

        executorService = Executors.newScheduledThreadPool(1);
        Duration duration = Duration.between(LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));
        Long delay = Math.abs(duration.toMillis());

        deActivateSchedule = executorService.schedule(new Task(blogger.getUserName(), blogger, bloggerRepository), delay, TimeUnit.MILLISECONDS);


//        this.deActivate = true;
//        while(this.deActivate){
//            try{
//                if (blogger.getStatus().equals("active"))
//                    blogger.setStatus("Inactive");
//                bloggerRepository.saveAndFlush(blogger);
//                Thread.sleep(15000);
//                System.out.println("Account deActivated");
//
//            } catch (InterruptedException e) {
//                this.deActivate = false;
//            }
        //}
    }

    @Override
    public void cancelDeactivation(Long bloggerId) {
        Optional<Blogger> findBlogger = bloggerRepository.findById(bloggerId);
        Blogger blogger = new Blogger();
        if (findBlogger.isPresent())
            blogger = findBlogger.get();
        else
            throw new UserNotFoundException("User not found");

        if (blogger.getStatus().equals("active")){
            if (!deActivateSchedule.isDone()){
                System.out.println("-----cancelling deactivation-----");
                deActivateSchedule.cancel(false);
            }

            System.out.println("Task is cancelled: "+ deActivateSchedule.isCancelled());
            executorService.shutdown();
        }
        else{
            System.out.println("User already deactivated");
        }
    }

//    public void cancel(){
//        this.deActivate = false;
//    }
}

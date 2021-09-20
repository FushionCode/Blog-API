package com.fushioncode.blogapitestingwithswagger.services.Impl;

import com.fushioncode.blogapitestingwithswagger.entity.Blogger;
import com.fushioncode.blogapitestingwithswagger.repositorties.BloggerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class BloggerServiceImplTest {

    @Mock
    private BloggerRepository bloggerRepository;

    @InjectMocks
    private BloggerServiceImpl bloggerServiceImpl;


    @Test
    void shouldCreateAccountSuccessfully() {
        var blogger = new Blogger();
        blogger.setFirstName("Jerry");
        blogger.setLastName("Smith");
        blogger.setEmail("smith.jerry@example.com");
        blogger.setUserName("Jsmith");
        blogger.setPassword("11111111");
        blogger.setGender("male");

        BDDMockito.given(bloggerRepository.save(blogger)).willReturn(blogger);
        Mockito.when(bloggerRepository.save(blogger)).then(invocation -> invocation.getArgument(0));
        Blogger regBlogger = bloggerServiceImpl.createAccount(blogger);

        assertThat(regBlogger).isNotNull();
        assertThat(regBlogger.getUserName()).isEqualTo(blogger.getUserName());
    }
}
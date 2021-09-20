package com.fushioncode.blogapitestingwithswagger.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( uniqueConstraints = {
        @UniqueConstraint(name = "email_unique", columnNames = "email_address"),
        @UniqueConstraint(name = "username_unique",columnNames = "user_name")
})
public class Blogger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blogger_id")
    private  Long id;
    private String firstName;
    private String lastName;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "email_address")
    private String email;
    private String gender;
    private String status = "active";
    private String password;
}

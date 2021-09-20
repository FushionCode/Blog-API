package com.fushioncode.blogapitestingwithswagger.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String postContent;
    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "blogger_id",
            referencedColumnName = "blogger_id"
    )
    private Blogger blogger;

    @OneToMany
    private Set<Blogger> likes;

    @OneToMany
    private Set<Blogger> dislike;
}

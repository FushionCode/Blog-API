package com.fushioncode.blogapitestingwithswagger.entity;

import com.fushioncode.blogapitestingwithswagger.entity.Blogger;
import com.fushioncode.blogapitestingwithswagger.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Blogger blogger;
    @ManyToMany
    @JoinTable(
            name = "favourite_post",
            joinColumns = @JoinColumn(
                    name = "favorite_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "post_id",
                    referencedColumnName = "id"
            )
    )
    private Set<Post> myFavoritePosts;
}

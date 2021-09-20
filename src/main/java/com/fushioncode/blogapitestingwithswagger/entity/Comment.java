package com.fushioncode.blogapitestingwithswagger.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String comment;

    @ManyToOne(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "post_id",
            referencedColumnName = "id"
    )
    private Post post;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "blogger_id",
            referencedColumnName = "blogger_id"
    )
    @ToString.Exclude
    private Blogger blogger;
}

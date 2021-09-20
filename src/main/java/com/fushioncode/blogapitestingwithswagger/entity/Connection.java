package com.fushioncode.blogapitestingwithswagger.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Connection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(
            name = "blogger_id",
            referencedColumnName = "blogger_id"
    )
    private Blogger blogger;

    @ManyToMany
    @JoinTable(
            name = "my_connections",
            joinColumns = @JoinColumn(
                    name = "connection_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "connected_blogger_id",
                    referencedColumnName = "blogger_id"
            )

    )
    private Set<Blogger> myConnections;
}

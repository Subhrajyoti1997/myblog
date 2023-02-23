package com.blog.myblog5.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="title",unique=true)
    private String title;
    @Column(name="description",nullable=false)
    private String description;
    private String content;
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true)
    Set<Comment> comments=new HashSet<>();
    //here we take Set bcz we want to not store duplicate data

}

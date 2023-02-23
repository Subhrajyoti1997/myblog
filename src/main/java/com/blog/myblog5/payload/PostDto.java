package com.blog.myblog5.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PostDto {
    private long id;
    @NotNull
    @Size(min=2,message="post title contains at least 2 char")
    private String title;
    @NotNull
    @Size(min=10,message="post description should have at least 10 char")
    private String description;
    @NotNull
    //if use @notnull it take spaces like if we put in postman  ""  then it take this to database
    @NotEmpty(message="content should not empty")
    private String content;
}

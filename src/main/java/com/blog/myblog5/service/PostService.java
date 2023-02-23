package com.blog.myblog5.service;

import com.blog.myblog5.payload.PostDto;
import com.blog.myblog5.payload.PostResponse;

public interface PostService {
    public PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(long id);
    PostDto updatePost(PostDto postDto,long id);
    void deletePost(long id);
}

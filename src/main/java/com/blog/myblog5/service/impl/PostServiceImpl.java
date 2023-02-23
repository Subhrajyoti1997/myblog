package com.blog.myblog5.service.impl;

import com.blog.myblog5.entity.Post;
import com.blog.myblog5.exception.ResourceNotFoundException;
import com.blog.myblog5.payload.PostDto;
import com.blog.myblog5.payload.PostResponse;
import com.blog.myblog5.repository.PostRepository;
import com.blog.myblog5.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepo;
    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepo,ModelMapper mapper) {
        this.postRepo = postRepo;
        this.mapper=mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = matToEntity(postDto);
        Post savePost = postRepo.save(post);
        return mapToDto(savePost);
    }


    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        //sortDir ingoring cases this sortDir.equalsIgnoreCase
        //comparing this sortDir.equalsIgnoreCase to this Sort.Direction.ASC.name()and if true print this Sort.by(sortBy).ascending()
        //if not true then print this Sort.by(sortBy).descending()
        //then assign local variable
        Pageable pageable= PageRequest.of(pageNo,pageSize, sort);
        //the return type of sortBy was Sort so we convert type string to type Sort and  write Sort.By(sortBy)
        Page<Post> pagePosts = postRepo.findAll(pageable);
        List<Post> posts = pagePosts.getContent();
        List<PostDto> contents = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(contents);
        postResponse.setPageNo(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setLastPage(pagePosts.isLast());
        return postResponse;

    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post updatePost = postRepo.save(post);
        return mapToDto(updatePost);
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepo.delete(post);
    }

    public Post matToEntity(PostDto postDto){
        Post post = mapper.map(postDto, Post.class);
//        Post post=new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
    public PostDto mapToDto(Post post){
        PostDto postDto = mapper.map(post, PostDto.class);
//        PostDto postDto=new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        return postDto;
    }
}

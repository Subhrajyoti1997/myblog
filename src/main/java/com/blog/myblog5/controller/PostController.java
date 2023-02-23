package com.blog.myblog5.controller;

import com.blog.myblog5.payload.PostDto;
import com.blog.myblog5.payload.PostResponse;
import com.blog.myblog5.service.PostService;
import com.blog.myblog5.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    //@PreAuthorize tells that only when the role is admin this method can run
    //means we putting constraints that who can access the methods
    @PostMapping
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostDto postDto,BindingResult bindingResult ){
        //after doing validation it ok but not giving any response and in postman not showing anything
        // so to do that use BindingResult
        //BindingResult will verify wheather errors there or not.
        // change return type to Object from PostDto bcz Object superMost class so upcasting done and object give any type of response Return
        if(bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    @GetMapping
//    public PostResponse getAllPosts(
//            @RequestParam (value="pageNo",defaultValue = "0",required=false)int pageNo,
//            @RequestParam(value="pageSize",defaultValue = "5",required=false) int pageSize,
//            @RequestParam(value="sortBy",defaultValue = "id",required = false) String sortBy,
//            @RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDir){
            //String sortBy bcz all the fields in entity class was like description ,title was string
    //the above line of code is hardCoded so to make flexible make a class in util layer AppConstants
    //and use in controller
    public PostResponse getAllPosts(
            @RequestParam (value="pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required=false)int pageNo,
            @RequestParam(value="pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required=false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
   }
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable("id") long id){
        PostDto dto = postService.updatePost(postDto, id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        postService.deletePost(id);
        return new ResponseEntity<> ("post entity deleted successfully",HttpStatus.OK);
    }
}

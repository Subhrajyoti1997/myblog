package com.blog.myblog5.service.impl;

import com.blog.myblog5.entity.Comment;
import com.blog.myblog5.entity.Post;
import com.blog.myblog5.exception.ResourceNotFoundException;
import com.blog.myblog5.payload.CommentDto;
import com.blog.myblog5.repository.CommentRepository;
import com.blog.myblog5.repository.PostRepository;
import com.blog.myblog5.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepo;
    private PostRepository postRepo;
    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepo, PostRepository postRepo,ModelMapper mapper) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.mapper=mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        //here we give long postId bcz we telling that for this post we save comment
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        Comment comment=mapToComment(commentDto);
        comment.setPost(post);//this line we specify save comment of this post
        Comment newComment = commentRepo.save(comment);
        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        List<Comment> comments = commentRepo.findByPostId(postId);
        List<CommentDto> commentDtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public CommentDto updateComment(long postId, long id, CommentDto commentDto) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        Comment comment = commentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", id));
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        Comment updatedComment = commentRepo.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));
        //commentRepo.deleteById(commentId);
        commentRepo.delete(comment);
    }

    Comment mapToComment(CommentDto commentDto){
        Comment comment = mapper.map(commentDto, Comment.class);
//        Comment comment=new Comment();
//        comment.setBody(commentDto.getBody());
//        comment.setEmail(commentDto.getEmail());
//        comment.setName(commentDto.getName());
        return comment;
    }
     CommentDto mapToDto(Comment comment){
         CommentDto commentDto = mapper.map(comment,CommentDto.class);
//        CommentDto commentDto=new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setBody(comment.getBody());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setName(comment.getName());
        return commentDto;
     }

}

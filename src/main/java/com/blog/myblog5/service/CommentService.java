package com.blog.myblog5.service;

import com.blog.myblog5.entity.Comment;
import com.blog.myblog5.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId,CommentDto commentDto);
    List<CommentDto> getCommentByPostId(long postId);
    CommentDto updateComment(long postId,long id,CommentDto commentDto);
    void deleteComment(long postId,long commentId);
}

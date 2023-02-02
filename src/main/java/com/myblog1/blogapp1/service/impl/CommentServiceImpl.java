package com.myblog1.blogapp1.service.impl;

import com.myblog1.blogapp1.entities.Comment;
import com.myblog1.blogapp1.entities.Post;
import com.myblog1.blogapp1.exception.ResourceNotFoundException;
import com.myblog1.blogapp1.payload.CommentDto;
import com.myblog1.blogapp1.repository.CommentRepository;
import com.myblog1.blogapp1.repository.PostRepository;
import com.myblog1.blogapp1.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepo;
    private PostRepository postRepo;
    private ModelMapper mapper;
    public CommentServiceImpl(CommentRepository commentRepo, PostRepository postRepo, ModelMapper mapper) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long postId,CommentDto commentDto) {

        Post post = postRepo.findById(postId)
                .orElseThrow( ()-> new ResourceNotFoundException("post","id",postId));

        Comment comment = mapToComment(commentDto);
        comment.setPost(post);
        Comment newComment = commentRepo.save(comment);
        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        List<Comment> comments = commentRepo.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto updateComment(long postId, long id, CommentDto commentDto) {
        Post post = postRepo.findById(postId).orElseThrow( 
                ()-> new ResourceNotFoundException("post","id",postId));

        Comment comment = commentRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("comment","id",id));

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment updatedComment = commentRepo.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepo.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("post","id",postId));

        Comment comment = commentRepo.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("comment","id",commentId));
        commentRepo.deleteById(commentId);

    }

    Comment mapToComment(CommentDto commentDto){

        Comment comment = mapper.map(commentDto, Comment.class);
       /* Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());*/
        return comment;
    }
    CommentDto mapToDto(Comment comment){

        CommentDto commentDto = mapper.map(comment, CommentDto.class);
       /* CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
       */

        return commentDto;

    }

}

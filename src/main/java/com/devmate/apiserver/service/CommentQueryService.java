package com.devmate.apiserver.service;

import com.devmate.apiserver.domain.Comment;
import com.devmate.apiserver.dto.comment.response.CommentDto;
import com.devmate.apiserver.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentQueryService {
    private final CommentRepository commentRepository;
    public CommentDto getCommentDto(Long id){
        Comment comment = commentRepository.findCommentAndMemberById(id).orElseThrow(
                () -> new NoSuchElementException("Not Found Comment"));
        return new CommentDto(comment);
    }
}

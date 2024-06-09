package com.devmate.apiserver.service;

import com.devmate.apiserver.common.exception.LackOfPermissionException;
import com.devmate.apiserver.domain.Comment;
import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.domain.Post;
import com.devmate.apiserver.dto.comment.request.CommentRequestDto;
import com.devmate.apiserver.repository.CommentRepository;
import com.devmate.apiserver.repository.MemberRepository;
import com.devmate.apiserver.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    @Transactional
    public Long commentSave(String loginId, Long postId, CommentRequestDto commentRequestDto){
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() ->
                new NoSuchElementException("Not Found Member"));
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new NoSuchElementException("Not Found Post"));

        Comment comment = new Comment(member, post, commentRequestDto);
        Comment savedComment = commentRepository.save(comment);
        post.addCommentCount();
        return savedComment.getId();
    }

    @Transactional
    public void commentDelete(String loginId, Long commentId){
        Comment comment = commentRepository.findCommentAndMemberById(commentId).orElseThrow(() -> new NoSuchElementException("Not Found Comment"));
        if(!comment.getMember().getLoginId().equals(loginId)){
            throw new LackOfPermissionException("Lack of Permission");
        }
        commentRepository.delete(comment);
    }

    @Transactional
    public Long commentEdit(String loginId, Long commentId, CommentRequestDto commentRequestDto){
        Comment comment = commentRepository.findCommentAndMemberById(commentId).orElseThrow(() -> new NoSuchElementException("Not Found Comment"));
        if(!comment.getMember().getLoginId().equals(loginId)){
            throw new LackOfPermissionException("Lack of Permission");
        }
        comment.changeContent(commentRequestDto.getComment());
        return comment.getId();
    }
}

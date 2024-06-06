package com.devmate.apiserver.dto.comment.response;

import com.devmate.apiserver.domain.Comment;
import com.devmate.apiserver.domain.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private String content;
    private LocalDateTime commentDateTime;
    private Writer writer;

    @Getter
    @Setter
    static class Writer{
        private String nickName;
        private String profileImgUrl;

        Writer(Member member){
            this.nickName = member.getNickName();
            this.profileImgUrl = member.getProfileImgUrl();
        }
    }

    public CommentDto(Comment comment){
        this.id= comment.getId();
        this.content = comment.getCommentBody();
        this.commentDateTime = comment.getCommentDateTime();
        this.writer = new Writer(comment.getMember());
    }
}


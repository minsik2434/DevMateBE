package com.devmate.apiserver.dto.comment.response;

import com.devmate.apiserver.domain.Comment;
import com.devmate.apiserver.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {
    @Schema(description = "댓글 ID", example = "예) 1")
    private Long id;
    @Schema(description = "댓글 내용", example = "예) 궁금합니다")
    private String content;
    @Schema(description = "댓글 등록 시간(LocalDateTime)", example = "예) 2024-06-19T14:51:41.67998")
    private LocalDateTime commentDateTime;

    private Writer writer;

    @Getter
    @Setter
    static class Writer{
        @Schema(description = "작성자 닉네임", example = "예) 제이드")
        private String nickName;
        @Schema(description = "작성자 프로필 이미지 URL", example = "예) https://img.test.com")
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


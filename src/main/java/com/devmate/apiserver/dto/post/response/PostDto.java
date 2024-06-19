package com.devmate.apiserver.dto.post.response;

import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.domain.Post;
import com.devmate.apiserver.domain.PostHashTag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostDto {
    @Schema(description = "게시글 ID", example = "예) 1")
    private Long id;
    @Schema(description = "게시글 제목", example = "예) Q&A 제목")
    private String title;
    @Schema(description = "게시글 등록 시간", example = "예) 2024-06-19T14:51:41.67998")
    private LocalDateTime postingDateTime;
    @Schema(description = "게시글 내용", example = "예) Q&A 내용입니다")
    private String content;
    @Schema(description = "게시글 조회수", example = "예) 0")
    private Integer viewCount;
    @Schema(description = "게시글 좋아요 수", example = "예) 0")
    private Integer goodCount;
    @Schema(description = "게시글 댓글 수", example = "예) 0")
    private Integer commentCount;
    @Schema(description = "게시글 category", example = "예) qna")
    private String category;
    @Schema(description = "게시글 tag", example = "예) spring")
    private List<String> tags = new ArrayList<>();
    private Writer writer;

    @Getter
    @Setter
    static class Writer{
        @Schema(description = "작성자 닉네임", example = "예) 제이드")
        private String nickName;
        @Schema(description = "작성자 프로필 이미지", example = "예) https://img.test.com")
        private String profileImgUrl;

        Writer(Member member){
            this.nickName = member.getNickName();
            this.profileImgUrl = member.getProfileImgUrl();
        }
    }
    public PostDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.postingDateTime = post.getPostingDateTime();
        this.content = post.getContent();
        this.viewCount = post.getViewCount();
        this.goodCount = post.getGoodCount();
        this.commentCount = post.getCommentCount();
        this.category = post.getDType();
        this.writer = new Writer(post.getMember());
        for (PostHashTag postHashTag : post.getPostHashTag()) {
            tags.add(postHashTag.getHashTag().getName());
        }
    }
}

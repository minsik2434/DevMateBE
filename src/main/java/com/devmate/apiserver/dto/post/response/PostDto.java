package com.devmate.apiserver.dto.post.response;

import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.domain.Post;
import com.devmate.apiserver.domain.PostHashTag;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostDto {

    private Long id;
    private String title;
    private LocalDateTime postingDateTime;
    private String content;
    private Integer viewCount;
    private Integer goodCount;
    private Integer commentCount;
    private List<String> tags = new ArrayList<>();
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
    public PostDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.postingDateTime = post.getPostingDateTime();
        this.content = post.getContent();
        this.viewCount = post.getViewCount();
        this.goodCount = post.getGoodCount();
        this.commentCount = post.getCommentCount();
        this.writer = new Writer(post.getMember());
        for (PostHashTag postHashTag : post.getPostHashTag()) {
            tags.add(postHashTag.getHashTag().getName());
        }
    }
}

package com.devmate.apiserver.dto.post.response;

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
    private String writer;
    public PostDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.postingDateTime = post.getPostingDateTime();
        this.content = post.getContent();
        this.viewCount = post.getViewCount();
        this.goodCount = post.getGoodCount();
        this.commentCount = post.getCommentCount();
        this.writer = post.getMember().getNickName();
        for (PostHashTag postHashTag : post.getPostHashTag()) {
            tags.add(postHashTag.getHashTag().getName());
        }
    }
}

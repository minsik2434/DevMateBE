package com.devmate.apiserver.domain;

import com.devmate.apiserver.dto.post.request.PostRegisterDto;
import com.devmate.apiserver.dto.post.request.RegisterDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="dtype")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public abstract class Post {
    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;
    private String title;
    private LocalDateTime postingDateTime;
    private Integer viewCount;
    private Integer goodCount;
    private Integer commentCount;
    @Column(name = "dtype" , insertable = false, updatable = false)
    private String dType;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post",cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, orphanRemoval = true)
    private List<PostHashTag> postHashTag = new ArrayList<>();

    public <T extends RegisterDto>Post(Member member, T registerDto){
        this.title = registerDto.getTitle();
        this.postingDateTime = LocalDateTime.now();
        this.viewCount = 0;
        this.goodCount = 0;
        this.commentCount = 0;
        this.content = registerDto.getContent();
        this.member = member;
    }
    public void addCommentCount(){
        this.commentCount = commentCount +1;
    }

    public void addViewCount(){
        this.viewCount = viewCount + 1;
    }
}

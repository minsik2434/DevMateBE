package com.devmate.apiserver.domain;

import com.devmate.apiserver.dto.member.request.EditProfileDto;
import com.devmate.apiserver.dto.member.request.MemberRegisterDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private String nickName;
    private boolean experienced;
    private String profileImgUrl;
    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<MemberInterest> memberInterests = new ArrayList<>();
    public Member(String loginId, String password, String name, String nickName, boolean experienced, String profileImgUrl) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.experienced = experienced;
        this.profileImgUrl = profileImgUrl;
    }

    public Member(MemberRegisterDto memberRegisterDto, String password) {
        this.loginId = memberRegisterDto.getLoginId();
        this.password = password;
        this.name = memberRegisterDto.getName();
        this.nickName = memberRegisterDto.getNickName();
        this.experienced = memberRegisterDto.getExperienced();
        this.profileImgUrl = "default.png";
    }

    public void editMember(EditProfileDto editProfileDto){
        this.name = editProfileDto.getName();
        this.nickName = editProfileDto.getNickName();
        this.experienced = editProfileDto.getExperienced();
        this.profileImgUrl = editProfileDto.getImgUrl();
    }
}

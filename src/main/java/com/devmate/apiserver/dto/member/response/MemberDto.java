package com.devmate.apiserver.dto.member.response;

import com.devmate.apiserver.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberDto {
    private String loginId;
    private String name;
    private String nickName;
    private Boolean experienced;
    private String profileImgUrl;

    public MemberDto(Member member){
        this.loginId = member.getLoginId();
        this.name = member.getName();
        this.nickName = member.getNickName();
        this.experienced = member.isExperienced();
        this.profileImgUrl = member.getProfileImgUrl();
    }
}

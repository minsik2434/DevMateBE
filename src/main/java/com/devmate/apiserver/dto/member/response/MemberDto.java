package com.devmate.apiserver.dto.member.response;

import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.domain.MemberInterest;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class MemberDto {
    private String loginId;
    private String name;
    private String nickName;
    private Boolean experienced;
    private String imgUrl;
    private List<Long> interests = new ArrayList<>();

    public MemberDto(Member member){
        this.loginId = member.getLoginId();
        this.name = member.getName();
        this.nickName = member.getNickName();
        this.experienced = member.isExperienced();
        this.imgUrl = member.getProfileImgUrl();
        for (MemberInterest memberInterest : member.getMemberInterests()) {
            interests.add(memberInterest.getInterest().getId());
        }
    }
}

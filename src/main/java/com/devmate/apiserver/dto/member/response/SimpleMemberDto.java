package com.devmate.apiserver.dto.member.response;

import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.domain.MemberInterest;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SimpleMemberDto {
    private String nickName;
    private String imgUrl;
    private List<Long> interests = new ArrayList<>();

    public SimpleMemberDto(Member member){
        this.nickName = member.getNickName();
        this.imgUrl = member.getProfileImgUrl();
        for (MemberInterest memberInterest : member.getMemberInterests()) {
            interests.add(memberInterest.getInterest().getId());
        }
    }
}

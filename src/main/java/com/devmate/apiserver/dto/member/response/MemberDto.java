package com.devmate.apiserver.dto.member.response;

import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.domain.MemberInterest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class MemberDto {
    @Schema(description = "회원 로그인 아이디", example = "예) qwer3059")
    private String loginId;
    @Schema(description = "회원 이름", example = "예) 이재호")
    private String name;
    @Schema(description = "회원 닉네임", example = "예) 제이드")
    private String nickName;
    @Schema(description = "회원 경력 유무", example = "예) false")
    private Boolean experienced;
    @Schema(description = "회원 프로필 URL", example = "예) https://img.test.com")
    private String imgUrl;
    @Schema(description = "회원 관심 직종 아이디", example = "예) [1,2,3]")
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

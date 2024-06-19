package com.devmate.apiserver.dto.interest.response;

import com.devmate.apiserver.domain.Interest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterestDto {
    @Schema(description = "관심 직종 ID", example = "예) 1")
    private Long id;
    @Schema(description = "관심 직종 ID", example = "예) Front End")
    private String name;
    
    public InterestDto(Interest interest){
        this.id = interest.getId();
        this.name = interest.getName();
    }
}

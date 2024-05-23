package com.devmate.apiserver.dto.interest.response;

import com.devmate.apiserver.domain.Interest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterestDto {
    private Long id;
    private String name;
    
    public InterestDto(Interest interest){
        this.id = interest.getId();
        this.name = interest.getName();
    }
}

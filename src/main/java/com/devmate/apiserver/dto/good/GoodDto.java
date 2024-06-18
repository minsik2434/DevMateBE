package com.devmate.apiserver.dto.good;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodDto {
    private Long goodId;

    public GoodDto(Long goodId){
        this.goodId = goodId;
    }
}

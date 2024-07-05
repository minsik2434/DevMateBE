package com.devmate.apiserver.dto.good;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckGoodDto {
    private Boolean isGood;

    public CheckGoodDto(boolean isGood){
        this.isGood = isGood;
    }
}

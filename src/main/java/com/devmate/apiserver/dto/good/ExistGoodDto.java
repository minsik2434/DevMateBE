package com.devmate.apiserver.dto.good;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExistGoodDto extends CheckGoodDto{
    private Long goodId;

    public ExistGoodDto(boolean isGood, Long goodId){
        super(isGood);
        this.goodId = goodId;
    }
}

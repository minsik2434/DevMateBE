package com.devmate.apiserver.dto.good;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodDto {
    @Schema(description = "좋아요 ID", example = "예) 1")
    private Long goodId;

    public GoodDto(Long goodId){
        this.goodId = goodId;
    }
}

package com.likelion.byuldajul.commit.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommitRequestDto {

    private int year;

    private int month;

    @Builder
    public CommitRequestDto(int year, int month) {
        this.year = year;
        this.month = month;
    }
}

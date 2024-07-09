package com.likelion.byuldajul.commit.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommitResponseDto {

    private int day;

    private int commitCount;

    @Builder
    public CommitResponseDto(int day, int commitCount) {
        this.day = day;
        this.commitCount = commitCount;
    }
}

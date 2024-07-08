package com.likelion.byuldajul.board.Dto.reponse;

import com.likelion.byuldajul.board.Entity.Diary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryListResponseDto {

    private Long Id;

    private LocalDateTime createdAt;

    private String title;

    private String mainText;

    public static DiaryListResponseDto from(Diary diary) {
        return DiaryListResponseDto.builder()
                .Id(diary.getId())
                .createdAt(diary.getCreatedAt())
                .title(diary.getTitle())
                .mainText(diary.getMainText())
                .build();

    }

}

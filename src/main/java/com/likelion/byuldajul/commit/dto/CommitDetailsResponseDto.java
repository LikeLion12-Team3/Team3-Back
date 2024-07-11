package com.likelion.byuldajul.commit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommitDetailsResponseDto {

    private Long id;

    private Long boardId;

    private LocalDate date;

    private String template;

    private String title;

    private String mainText;

    private String impression;

    private String remark;

    private String plan;

    private List<String> hashtags;

    @Builder
    public CommitDetailsResponseDto(Long id, Long boardId, LocalDate date, String title, String mainText, String impression, List<String> hashtags) {
        this.id = id;
        this.boardId = boardId;
        this.date = date;
        this.title = title;
        this.mainText = mainText;
        this.impression = impression;
        this.hashtags = hashtags;
    }
}

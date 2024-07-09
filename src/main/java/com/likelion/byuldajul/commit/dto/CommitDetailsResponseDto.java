package com.likelion.byuldajul.commit.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommitDetailsResponseDto {

    private Long id;

    private Long boardId;

    private LocalDateTime date;

    private String title;

    private String mainText;

    private String impression;

    private List<String> hashtags;

    @Builder
    public CommitDetailsResponseDto(Long id, Long boardId, LocalDateTime date, String title, String mainText, String impression, List<String> hashtags) {
        this.id = id;
        this.boardId = boardId;
        this.date = date;
        this.title = title;
        this.mainText = mainText;
        this.impression = impression;
        this.hashtags = hashtags;
    }
}

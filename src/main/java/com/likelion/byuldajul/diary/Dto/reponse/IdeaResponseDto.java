package com.likelion.byuldajul.diary.Dto.reponse;

import com.likelion.byuldajul.diary.Entity.Idea;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IdeaResponseDto {

    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private String title;

    private String mainText;



    public static IdeaResponseDto from(Idea idea) {
        return IdeaResponseDto.builder()
                .id(idea.getId())
                .createdAt(idea.getCreatedAt())
                .modifiedAt(idea.getModifiedAt())
                .title(idea.getTitle())
                .mainText(idea.getMainText())
                .build();
    }
}

package com.likelion.byuldajul.board.Dto.reponse;

import com.likelion.byuldajul.board.Entity.Diary;
import com.likelion.byuldajul.board.Entity.DiaryHashtag;
import com.likelion.byuldajul.board.Entity.Hashtag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiaryResponseDto {

    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private String title;

    private String template;

    private String mainText;

    private String impression;

    private String remark;

    private String plan;

    private List<String> hashtagNames;

}

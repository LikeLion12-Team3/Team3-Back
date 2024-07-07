package com.likelion.byuldajul.board.Dto.request;

import com.likelion.byuldajul.board.Entity.Diary;
import com.likelion.byuldajul.board.Entity.DiaryHashtag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateDiaryRequestDto {

    private String title;

    private String template;

    private String mainText;

    private String impression;

    private List<String> diaryHashtags = new ArrayList<>();

    public Diary toEntity(){
        return Diary.builder()
                .title(title)
                .template(template)
                .mainText(mainText)
                .impression(impression)
                .build();
    }



}

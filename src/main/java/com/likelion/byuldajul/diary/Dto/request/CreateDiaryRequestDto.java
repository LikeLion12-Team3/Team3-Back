package com.likelion.byuldajul.diary.Dto.request;

import com.likelion.byuldajul.diary.Entity.Diary;
import com.likelion.byuldajul.diary.Entity.Hashtag;
import com.likelion.byuldajul.user.entity.User;
import lombok.AllArgsConstructor;
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

    private String remark;

    private String plan;

    private List<String> diaryHashtags = new ArrayList<>();

    public Diary toEntity(User user){
        return Diary.builder()
                .title(title)
                .template(template)
                .mainText(mainText)
                .impression(impression)
                .remark(remark)
                .plan(plan)
                .user(user)
                .build();
    }



}

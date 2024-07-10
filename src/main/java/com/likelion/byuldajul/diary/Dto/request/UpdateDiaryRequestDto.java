package com.likelion.byuldajul.diary.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateDiaryRequestDto {

    public Long id;

    public String title;

    public String template;

    public String mainText;

    public String impression;

    public String remark;

    public String plan;

    public List<String> diaryHashtags = new ArrayList<>();

}

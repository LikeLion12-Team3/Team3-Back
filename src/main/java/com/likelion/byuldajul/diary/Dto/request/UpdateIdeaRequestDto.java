package com.likelion.byuldajul.diary.Dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateIdeaRequestDto {

    public Long id;

    public String title;

    public String mainText;
}
package com.likelion.byuldajul.diary.Dto.request;

import com.likelion.byuldajul.diary.Entity.Idea;
import com.likelion.byuldajul.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateIdeaRequestDto {

    private String title;

    private String mainText;


    public Idea toEntity(User user) {
        return Idea.builder()
                .title(title)
                .mainText(mainText)
                .user(user)
                .build();
    }

}

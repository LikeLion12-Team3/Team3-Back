package com.likelion.byuldajul.analysis.Dto.response;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GPTResponseDto {
    private List<Choice> choices;

    @Data
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Choice {
        //gpt 대화 인덱스 번호
        private int index;
        // 지피티로 부터 받은 메세지
        // 여기서 content는 유저의 prompt가 아닌 gpt로부터 받은 response
        private GPTMessageDto message;
    }
}

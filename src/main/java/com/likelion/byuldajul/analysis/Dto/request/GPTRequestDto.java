package com.likelion.byuldajul.analysis.Dto.request;

import com.likelion.byuldajul.analysis.Dto.response.GPTMessageDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class GPTRequestDto {

    private String model;
    private List<GPTMessageDto> messages;
    private Double temperature;
    private Integer maxTokens;
    private Double topP;
    private Double frequencyPenalty;
    private Double presencePenalty;

    // 빌더 패턴 사용 예시
    public static void main(String[] args) {
        GPTRequestDto requestDto = GPTRequestDto.builder()
                .model("gpt-3.5-turbo")
                .messages(new ArrayList<>())
                .temperature(0.2)
                .maxTokens(100)
                .topP(1.0)
                .frequencyPenalty(0.0)
                .presencePenalty(0.0)
                .build();

        // 메시지 추가
        requestDto.getMessages().add(new GPTMessageDto("user", "Hello, world!"));
    }

}
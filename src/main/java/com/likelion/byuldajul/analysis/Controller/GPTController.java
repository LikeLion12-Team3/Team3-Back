package com.likelion.byuldajul.analysis.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.likelion.byuldajul.analysis.Dto.request.GPTRequestDto;
import com.likelion.byuldajul.analysis.Dto.response.GPTMessageDto;
import com.likelion.byuldajul.analysis.Dto.response.GPTResponseDto;

import java.util.Collections;


@RestController
public class GPTController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.key}")
    private String apiKey;

    @Value("${api.url}")
    private String apiUrl;

    @GetMapping("/chat")
    public ResponseEntity<?> chat(@RequestParam("prompt") String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey); // OpenAI API 키 설정

        // OpenAI Chat Completion API 요청에 맞는 GPTRequestDto 객체 생성 (빌더 패턴 사용)
        GPTRequestDto requestDto = GPTRequestDto.builder()
                .model("gpt-3.5-turbo")
                .messages(Collections.singletonList(new GPTMessageDto("user", prompt))) // prompt를 메시지로 추가
                .temperature(0.2)
                .maxTokens(100)
                .topP(1.0)
                .frequencyPenalty(0.0)
                .presencePenalty(0.0)
                .build();

        HttpEntity<GPTRequestDto> entity = new HttpEntity<>(requestDto, headers);

        try {
            GPTResponseDto responseDto = restTemplate.postForObject(apiUrl, entity, GPTResponseDto.class);
            if (responseDto != null && responseDto.getChoices() != null && !responseDto.getChoices().isEmpty()) {
                return ResponseEntity.ok(responseDto.getChoices().get(0).getMessage().getContent());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing GPT request");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing GPT request: " + e.getMessage());
        }
    }
}
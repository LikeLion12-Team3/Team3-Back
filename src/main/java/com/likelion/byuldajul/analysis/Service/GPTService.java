package com.likelion.byuldajul.analysis.Service;

import com.likelion.byuldajul.analysis.Dto.request.GPTRequestDto;
import com.likelion.byuldajul.analysis.Dto.response.GPTMessageDto;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class GPTService {

    @Value("${api.url}")
    private String apiUrl;

    @Value("${api.key}")
    private String apiKey;

    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 문자열과 Java 객체 간 변환을 위한 ObjectMapper 인스턴스

    //주어진 내용(content)과 날짜(date)를 기반으로 요약을 생성
    public String generateSummary(String content, LocalDateTime date) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        //headers.set("Authorization", apiKey);
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON); // 요청 본문의 타입을 JSON으로 설정

        // GPTRequestDto를 사용하여 요청 바디 생성, @Builder 어노테이션을 사용하여 객체 생성
        GPTRequestDto requestDto = GPTRequestDto.builder()
                .model("gpt-3.5-turbo")
                .messages(Collections.singletonList(new GPTMessageDto("user", content)))
                .build();

        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(requestDto); // GPTRequestDto를 JSON 문자열로 변환
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        String response = restTemplate.postForObject(apiUrl, entity, String.class);

        return response;
    }
}
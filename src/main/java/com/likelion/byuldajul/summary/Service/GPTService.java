package com.likelion.byuldajul.summary.Service;

import com.likelion.byuldajul.summary.Dto.GPTMessageDto;
import com.likelion.byuldajul.summary.Dto.request.GPTRequestDto;

import com.likelion.byuldajul.summary.Dto.response.GPTErrorResponseDto;
import com.likelion.byuldajul.summary.Dto.response.GPTResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class GPTService {

    @Value("${gpt.api.url}")
    private String apiUrl;

    @Value("${gpt.api.key}")
    private String apiKey;

    //주어진 내용(content)
    public String generateSummary(List<String> content){

        // 프롬프트 메시지
        String assistantContent = "당신은 개발자와 디자이너가 프로젝트 진행 중 기록한 일기를 요약하는 AI 요약 도구입니다. " +
                "이 요약은 사용자가 포트폴리오와 지원서를 작성할 때 유용하게 사용할 수 있도록 돕기 위한 것입니다. " +
                "사용자가 제공한 여러 개의 일기를 읽고, 각 일기의 핵심 포인트를 요약해 주세요. " +
                "각 일기는 프로젝트 작업의 세부 내용, 문제 해결 과정, 성과, 배운 점 등을 포함할 수 있습니다. " +
                "요약의 목적은 사용자가 프로젝트 경험을 효과적으로 정리하여 포트폴리오나 지원서에 활용할 수 있도록 하는 것입니다. " +
                "요약 시 고려할 사항에는 프로젝트의 주요 목표와 성과, 해결한 문제와 그 과정, 작업 중 배운 점과 느낀 점, 사용한 기술과 도구, 중요한 결과물과 그 의미 등이 포함됩니다. " +
                "매우 중요한 포인트: 요약 형식은 중요 포인트를 중심으로 간결하고 명확하게 일기 하나에 한 줄을 요약하며, '~했습니다.'와 같은 자연스러운 말투를 사용합니다. " +
                "사용자의 일기들을 요약한 후, 순서대로 나열해 주세요. 또한, 일기에 작성된 내용만 요약하고 당신이 새로 생성하는 내용이나 문장은 추가하지 마세요.";

        GPTMessageDto assistantMessage = GPTMessageDto.builder()
                .role("assistant")
                .content(assistantContent)
                .build();

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i <= content.size(); i++) {
            String title = i + "번째 일기 : ";
            String diaryContent = content.get(i-1);
            stringBuilder.append(title).append(diaryContent).append("\n");
        }

        GPTMessageDto userMessage = GPTMessageDto.builder()
                .role("user")
                .content(stringBuilder.toString())
                .build();

        List<GPTMessageDto> messages = new ArrayList<>();
        messages.add(assistantMessage);
        messages.add(userMessage);

        GPTRequestDto gptRequestDto = GPTRequestDto.builder()
                .model("gpt-4o")
//              .temperature(0.2)
                .messages(messages)
//              .maxTokens(2000)
//              .topP(1.0)
                .build();

        Mono<GPTRequestDto> gptRequestDtoMono = Mono.just(gptRequestDto);

        GPTResponseDto resposne = WebClient.create(apiUrl)
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer "+apiKey)
                .body(gptRequestDtoMono, GPTRequestDto.class)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse
                        -> Mono.error(new RuntimeException(clientResponse.toString())))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse
                        -> Mono.error(new RuntimeException(clientResponse.toString())))
                .bodyToMono(GPTResponseDto.class)
                .block();

        if (resposne.getChoices().isEmpty()) {
            log.info("Choice 없음");
            throw new RuntimeException();
        }

        log.info("response message -> {} ", resposne.getChoices().get(0));
        String result = resposne.getChoices().get(0).getMessage().getContent();


        return result;
    }
}
package com.likelion.byuldajul.summary.Dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.likelion.byuldajul.summary.Dto.GPTMessageDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GPTRequestDto {

    @JsonProperty("model")
    private String model;

    @JsonProperty("messages")
    private List<GPTMessageDto> messages;

    @JsonProperty("temperature")
    private Double temperature;

    @JsonProperty("max_tokens")
    private Integer maxTokens;

    @JsonProperty("top_p")
    private Double topP;

    @JsonProperty("frequency_penalty")
    private Double frequencyPenalty;

    @JsonProperty("presence_penalty")
    private Double presencePenalty;

}
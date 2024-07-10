package com.likelion.byuldajul.summary.Service;

import com.likelion.byuldajul.summary.Dto.response.SummaryResponseDto;
import com.likelion.byuldajul.summary.Entity.Summary;
import com.likelion.byuldajul.summary.Repository.SummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SummaryService {

    private final SummaryRepository summaryRepository;

    public SummaryResponseDto getSummary(String email, LocalDate date) {
        Optional<Summary> summary = summaryRepository.findByEmailAndAndDate(email, date);

        if (summary.isEmpty()) {
            return SummaryResponseDto.builder().build();
        }
        return SummaryResponseDto.builder().summary(summary.get().getContent()).build();
    }
}

package com.likelion.byuldajul.diary.Service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;

@SpringBootTest
@Slf4j
public class DiaryServiceTest {

    @Autowired
    private DiaryService diaryService;

    @Test
    public void testAsyncMethod() throws InterruptedException {
        String email = "user@example.com";
        LocalDate date = LocalDate.now();

        log.info("비동기 메소드 호출 전");
        diaryService.updateDiarySummary(email, date);
        log.info("비동기 메소드 호출 직후");

        Thread.sleep(1000); // 1초 대기

        log.info("비동기 작업 완료 후");
    }
}
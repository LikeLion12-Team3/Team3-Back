package com.likelion.byuldajul.diary.Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.LocalDate;

@SpringBootTest
public class DiaryServiceTest {

    @Autowired
    private DiaryService diaryService;

    @Test
    public void testUpdateDiarySummaryAsync() throws Exception {
        String email = "test@example.com";
        LocalDate localDate = LocalDate.now();

        // 비동기 메소드 호출
        diaryService.updateDiarySummary(email, localDate);

        // 비동기 메소드가 바로 반환되어야 함을 확인
        // 실제 비동기 작업의 완료를 기다리지 않고, 메소드 호출 후 바로 다음 라인으로 넘어가는 것을 확인
        // 이 부분에서 추가적인 로직으로 비동기 작업의 완료를 확인할 수 있지만,
        // @Async 메소드의 경우 반환값을 확인하기 어렵기 때문에, 로그나 다른 방법으로 실제 작업 완료를 확인해야 합니다.
        System.out.println("비동기 메소드 호출 후 바로 출력되어야 함");

        // 여기서는 예시를 위한 간단한 출력만 진행
        // 실제로는 CompletableFuture 등을 사용하여 비동기 작업의 결과를 기다리고 확인하는 로직이 필요할 수 있습니다.
    }
}

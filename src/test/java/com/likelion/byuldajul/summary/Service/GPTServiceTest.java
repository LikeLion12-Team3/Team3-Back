package com.likelion.byuldajul.summary.Service;

import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class GPTServiceTest {

    private final GPTService gptService;

    @Autowired
    GPTServiceTest(GPTService gptService) {
        this.gptService = gptService;
    }

    @Test
    void gptTest() {
        String content1 = "1-1. save() 의 리소스 낭비? save() 메서드 내에서는 실제 SQL 쿼리문을 실행하기 전에 INSERT 명령을 할 것인지, UPDATE 명령을 할 것인지 나뉘게 되는 분기점이 존재한다. 위 내용과 같이 엔티티의 상태를 통해 결정하게 되는데, 이를 위해서 save() 메서드에서도 조회가 일어나게 된다. 따라서, 트랜잭션이 유지되는 상태일 때 엔티티 수정 후 save() 메서드는 리소스 낭비가 될 수 있다 예시 1) public void createUser(String name) {User user = User.builder().name(name).build();userRepository.save(user); //조회 1 + 삽입 1}위 코드에선 새로운 엔티티(New) 이므로,save() 메서드가 실행되는 순간 새로운 엔티티인지 검사(조회 1번)하고, 트랜잭션이 모두 끝나면 INSERT 쿼리문이 실행 될 것이다.";
        String content2 = "5-2. KakaoLoginController (최종)\n" +
                "KakaoLoginController (최종)\n" +
                "@Slf4j\n" +
                "@RestController\n" +
                "@RequiredArgsConstructor\n" +
                "@RequestMapping(\"\")\n" +
                "public class KakaoLoginController {\n" +
                "\n" +
                "    private final KakaoService kakaoService;\n" +
                "\n" +
                "    @GetMapping(\"/callback\")\n" +
                "    public ResponseEntity<?> callback(@RequestParam(\"code\") String code) {\n" +
                "        String accessToken = kakaoService.getAccessTokenFromKakao(code);\n" +
                "\n" +
                "        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);\n" +
                "\n" +
                "        // User 로그인, 또는 회원가입 로직 추가\n" +
                "        return new ResponseEntity<>(HttpStatus.OK);\n" +
                "    }\n" +
                "}\n" +
                " \n" +
                "\n" +
                " \n" +
                "\n" +
                "이제 로그인을 통해 사용자가 동의한 정보들을 얻을 수 있다.\n" +
                "\n" +
                " \n" +
                "\n" +
                "카카오로부터 받은 정보들을 통해서 서버에 회원가입 로직을 추가하면 되겠다.\n" +
                "\n" +
                " \n" +
                "\n" +
                "카카오에서 받은 토큰으로 인가를 해도 되고, 서버 내에서 사용중인 인가 방법이 있다면 새로 발급해줘도 되겠다.\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "카카오 공식 문서를 보면,\n" +
                "\n" +
                "추가 항목 동의 받기\n" +
                "카카오톡에서 자동 로그인하기\n" +
                "약관 선택해 동의 받기\n" +
                "OpenID Connect ID 토큰 발급하기\n" +
                "기존 로그인 여부와 상관없이 로그인하기\n" +
                "카카오계정 가입 후 로그인하기\n" +
                "로그인 힌트 주기\n" +
                "카카오계정 간편 로그인\n" +
                "등등 기능을 추가로 이용할 수 있으니, 필요한 서비스는 참고해서 이용하면 되겠다.";

        List<String> request = new ArrayList<>();
        request.add(content1);
        request.add(content2);
        String result = gptService.generateSummary(request);

        Assert.notNull(result);
        System.out.println("result  : " + result);
    }


}
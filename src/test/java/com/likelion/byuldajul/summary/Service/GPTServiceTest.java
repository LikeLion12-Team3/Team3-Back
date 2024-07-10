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
        String content1 = "1. save() 의 리소스 낭비? save() 메서드 내에서는 실제 SQL 쿼리문을 실행하기 전에 INSERT 명령을 할 것인지, UPDATE 명령을 할 것인지 나뉘게 되는 분기점이 존재한다. 위 내용과 같이 엔티티의 상태를 통해 결정하게 되는데, 이를 위해서 save() 메서드에서도 조회가 일어나게 된다. 따라서, 트랜잭션이 유지되는 상태일 때 엔티티 수정 후 save() 메서드는 리소스 낭비가 될 수 있다 예시 1) public void createUser(String name) {User user = User.builder().name(name).build();userRepository.save(user); //조회 1 + 삽입 1}위 코드에선 새로운 엔티티(New) 이므로,save() 메서드가 실행되는 순간 새로운 엔티티인지 검사(조회 1번)하고, 트랜잭션이 모두 끝나면 INSERT 쿼리문이 실행 될 것이다.";
        String content2 = "2. 오늘은 서비스 계층에서 발생할 수 있는 다양한 예외 상황에 대해 고민해보았다. 예를 들어, 사용자가 요청한 데이터가 존재하지 않을 때, 데이터가 중복될 때, 혹은 데이터 무결성에 위반되는 상황 등을 처리해야 한다. 이를 위해 @Transactional 어노테이션과 함께 예외 발생 시 롤백하는 메커니즘을 적용했다. 이는 서비스의 신뢰성을 높이는 데 도움이 되었다.";
        String content3 = "3. JPA를 사용하면서 엔티티 간의 연관 관계에서 지연 로딩(Lazy Loading)에 대해 학습했다. 초기에는 모든 연관된 데이터를 즉시 로딩(Eager Loading)했지만, 성능 문제가 발생했다. 이를 해결하기 위해 지연 로딩을 도입하여 필요할 때만 데이터를 로딩하도록 변경했다. 이는 초기 로딩 시간을 단축시키고, 메모리 사용량을 줄이는 데 효과적이었다.";
        String content4 = "4. 오늘은 데이터베이스 인덱스 최적화 작업을 진행했다. 특히, 자주 조회되는 컬럼에 대해 인덱스를 추가하여 쿼리 성능을 개선했다. 또한, 불필요한 인덱스를 제거하여 데이터 삽입 및 업데이트 성능을 저하시키는 요인을 제거했다. 최적화 후 성능 테스트를 통해 쿼리 속도가 눈에 띄게 향상된 것을 확인할 수 있었다.";
        String content5 = "5. 시스템 성능을 향상시키기 위해 Redis를 활용한 캐싱 메커니즘을 구현했다. 자주 조회되는 데이터를 Redis에 캐싱하여 데이터베이스 부하를 줄였다. 이를 통해 응답 속도가 빨라졌고, 특히 반복되는 조회 요청에서 큰 성능 향상을 경험했다. 캐시 만료 정책과 갱신 전략을 함께 설계하여 일관성을 유지할 수 있었다.";
        String content6 = "6. API를 개발하면서 버전 관리를 어떻게 할지 고민하게 되었다. 클라이언트와의 호환성을 유지하면서 새로운 기능을 추가하기 위해 버전별로 엔드포인트를 분리하는 전략을 채택했다. 이를 위해 URL 경로에 버전 정보를 포함시키고, 각 버전별로 컨트롤러를 분리하여 유지보수성을 높였다.";
        String content7 = "7. 오늘은 Spring Security를 이용해 애플리케이션의 인증과 권한 부여를 구현했다. JWT(JSON Web Token)를 사용해 토큰 기반 인증을 적용하고, 각 사용자별 역할(Role)에 따라 접근 권한을 설정했다. 이를 통해 보다 안전하고 관리하기 쉬운 인증 체계를 구축할 수 있었다.";
        String content8 = "8. Hibernate의 2차 캐시를 활용하여 데이터베이스 접근을 최소화하는 작업을 진행했다. 애플리케이션 전역에서 자주 사용하는 데이터에 대해 2차 캐시를 적용하여, 동일한 데이터에 대한 반복적인 조회를 줄였다. 이를 통해 데이터베이스 부하를 감소시키고 응답 속도를 개선할 수 있었다.";
        String content9 = "9. 테스트 자동화 도구인 JUnit과 Mockito를 이용해 단위 테스트와 통합 테스트를 작성했다. 지속적인 코드 변경에도 안정성을 유지하기 위해 CI/CD 파이프라인에 테스트 자동화를 포함시켰다. 이를 통해 코드의 품질을 높이고, 배포 주기를 단축할 수 있었다.";
        String content10 = "10. 모놀리식 애플리케이션에서 마이크로서비스 아키텍처로 전환하는 작업을 시작했다. 서비스별로 독립적으로 배포하고 확장할 수 있도록 애플리케이션을 분리했다. 이를 통해 개발 속도를 높이고, 각 서비스의 책임을 명확히 하여 유지보수성을 높였다.";
        String content11 = "11. REST API의 한계를 극복하기 위해 GraphQL을 도입했다. 클라이언트가 필요한 데이터만 정확히 요청할 수 있도록 쿼리 언어를 활용하고, 데이터 페치 횟수를 줄여 성능을 최적화했다. 초기 학습 비용이 있었지만, 유연한 데이터 조회와 효율적인 데이터 전송이 가능해졌다.";

        List<String> request = new ArrayList<>();
        String[] contents = {content1, content2, content3, content4, content5, content6, content7, content8, content9, content10, content11};

        for (String content : contents) {
            request.add(content);
        }

        String result = gptService.generateSummary(request);

        Assert.notNull(result);
        System.out.println("result  : " + result);
    }


}
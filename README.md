![DALL·E 2025-01-16 03 50 58 - A simple and clean logo-style design with the text 'Catch Culture Cloud' in bold white sans-serif font  The background is a bold and darker green colo webp](https://github.com/user-attachments/assets/83adde84-5ba7-4e0d-8ed2-189bc69f3b7a)

### 🛠 기술 스택

---

- Spring, Spring Boot, Spring Cloud, Spring Data JPA, QueryDSL, MySQL
- Redis, Docker, S3, Kafka

### 📖 프로젝트 소개

---

그간 수업에서 배운 내용들과 [**인프런 강의**](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%81%B4%EB%9D%BC%EC%9A%B0%EB%93%9C-%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C%EC%84%9C%EB%B9%84%EC%8A%A4?srsltid=AfmBOoozrh_zjW2v2TxijKTyaiuAwJTpnfo6iRzTJyKHgMjHf_KBU0lH)를 통해 익힌 기술들을 바탕으로, 이전 [**Catch Culture 프로젝트**](https://github.com/naver0504/catch-culture)에서 아쉬웠던 부분들을 개선한 프로젝트입니다.

이번 프로젝트에서는 각 서비스가 독립적으로 관리되고 확장될 수 있도록 **도메인 중심으로 분리**했습니다.

- **user-service**: 사용자와 관련된 데이터인 **사용자 정보**와 **포인트 내역**과 같은 엔티티를 중심으로 설계했고 **로그인 및 JWT 토큰 발급** 기능을 포함합니다. 이를 통해 사용자의 **인증 방식 변경**, **포인트 정책 추가** 등은 다른 서비스에 영향을 주지 않고 처리할 수 있습니다.
- **event-service**: 문화 행사와 관련된 엔티티를 관리하는 핵심 서비스로, **문화 행사, 방문 인증, 리뷰, 사용자 상호작용**과 같은 엔티티를 한곳에 묶어 구성했고 **문화 행사 정보 조회, 리뷰 작성 및 수정, 삭제**와 같은 기능을 제공합니다.
- **report-service**: **문화 행사 신청** 및 **방문 인증 신청**과 같은 엔티티를 관리하며, **요청 및 보고**와 관련된 작업을 담당하는 서비스입니다. **보고 데이터 처리 로직**이나 **승인 정책**을 변경해야 할 경우, report-service에서 독립적으로 관리할 수 있습니다.

### 🎛️ 시스템 아키텍쳐

---

![Catch Culture cloud (1)](https://github.com/user-attachments/assets/3172131f-b461-46b4-8a60-c39897ee1e39)


- **Eureka**를 사용하여 **Registry Service**를 구성하고, **user-service**, **event-service**, **report-service**를 **Eureka Server**에 ****등록했습니다.
- 각 서비스는 서로 데이터를 주고받기 위해 **Spring Cloud OpenFeign**을 사용하여 **REST API** 기반의 간편한 통신을 구현했고 또한, **Kafka**를 활용해 비동기 메시징을 처리합니다.
- 외부에서 들어오는 클라이언트 요청은 **Spring Cloud API Gateway**를 통해 각 서비스로 **라우팅**되며, API Gateway는 **JWT 토큰 검증**을 통해 요청의 유효성을 확인합니다.
- **event-service**에서는 **Redis**를 사용하여 **분산 Lock 메커니즘**을 구현했습니다. **Redis**는 **Kafka**와 함께 **Docker**를 사용하여 컨테이너화하였습니다.

### ✈️ 개선한 점

---

1. **데이터 베이스 관련**
    
    DB 관련 **인덱스 설정** 및 **로직 변경**을 통해 조회 성능을 향상시켰습니다.
    
    - **문화 행사 조회** 시 **카테고리 필터**를 적용할 때, 사용자가 카테고리를 선택하지 않은 경우에도 효율적인 조회를 위해 **모든 카테고리 값을 IN 절**에 포함시켰습니다. 이를 통해 항상 **카테고리 관련 인덱스**를 활용하게 하였습니다.
    - **report-service**에서 **No Offset 방식**을 도입하여 데이터 조회를 최적화했습니다. 이 방식을 통해 **Offset**으로 인한 불필요한 데이터 스캔을 방지하고, 아무리 페이지가 뒤로 가더라도 **처음 페이지를 읽은 것과 동일한 성능**을 가지게 했습니다. 이 과정에서 [**기술 불로그**](https://jojoldu.tistory.com/528)를 참고하였습니다.
2. **코드 가독성 향상**
    - 기존에는 클래스 내부에서 소셜 타입별로 `if` 문을 사용해 **Google, Naver, Kakao** 각각의 사용자 정보를 생성하는 로직을 관리했지만, 이 로직을 `SocialType` 내부로 옮기고 **함수형 인터페이스**를 활용하도록 변경하여 코드의 **가독성** 향상했고 **유지보수성**을 강화했습니다.
    - 데이터베이스 접근을 효율적으로 관리하기 위해 `Adapter` 인터페이스를 도입했습니다. `Service`단은 이 `Adapter`를 통해서만 **데이터베이스 관련 로직**에 접근하도록 설계함으로써, `Service` 단과 **DB 접근** **로직**을 **명확히 분리**했습니다. ****이를 통해 여러 `Repository`를 직접 주입받거나 사용하는 **번거로움**이 사라졌습니다.
    
    ![image (2)](https://github.com/user-attachments/assets/2de5a297-a68d-40bb-96b7-f159bec7864b)

    - 위 사진처럼 `KafkaTransactional`이라는 **커스텀 어노테이션**을 도입하여, **비즈니스 로직**과 **성공 및 실패 시 특정 이벤트**를 발행하는 로직을 **AOP**를 통해 분리했습니다. 기존에는 각 메서드마다 **성공/실패 이벤트 발행 로직**을 작성해야 했지만, **AOP**를 활용하여 해당 로직을 **명시적으로** 처리할 수 있게 되었습니다.
3. **동시성 문제 해결**
    - **좋아요 및 즐겨찾기** 관련 동시성 테스트 시, 단순 `SELECT` 조회로 인해 **문제**가 발생했습니다. **동시성 문제**의 원인은 자식 엔티티 저장 시 해당 **부모 엔티티**에 대해 **`S-Lock`**을 획득한  후 **수정**을 위해 `X-Lock`을 획득하려 했지만, 동시에 요청된 **다른 트랜잭션**이 이미 `S-Lock`을 가지고 있어 `X-Lock`을 획득하지 못하고 **DeadLock 상태**에 빠지는 것이었습니다.
        
        이 문제를 해결하기 위해 먼저 접근한 **트랜잭션**이 `SELECT FOR UPDATE`를 통해 `X-Lock`을 획득하도록 설계하여 **동시성 문제**를 해결했습니다. 그러나 이 방식은 `X-Lock`을 획득하기 위해 **커넥션을 유지**한 상태로 대기해야 하기 때문에 **DB에 부하**를 초래할 수 있다는 **한계**가 있었습니다.
        
        이를 개선하기 위해 **Redisson**을 활용하여 **DB** 접근 전에 **분산 Lock**을 설정하도록 변경했습니다. **Redisson**을 통해 **Lock을 미리 획득**한 후 **데이터베이스**에 접근함으로써 **Deadlock**을 방지하고, 시스템의 **안정성과 성능**을 동시에 향상시킬 수 있었습니다. 이 과정에서 관련 문제를 다룬 **[컬리의 기술 블로그](https://helloworld.kurly.com/blog/distributed-redisson-lock/)**를 참고하여 문제를 분석하고 해결 방안을 설계했습니다.
        

### 🧐 느낀 점

---

프로젝트를 진행하면서 MSA에서 데이터 일관성을 어떻게 유지해야 하는지, Kafka의 동작 원리는 무엇인지에 대해 충분히 이해하지 못한 상태에서 진행했던 것 같습니다. 이러한 부족함은 프로젝트 과정에서 시행착오로 나타났고, 이를 해결하기 위해 많은 시간과 노력을 투자해야 했습니다.

이 경험을 통해 기술의 원리를 제대로 이해하지 않은 상태에서 프로젝트를 진행하는 것이 얼마나 비효율적일 수 있는 지를 깨달았고, 기본적인 개념과 동작 원리에 대한 깊이 있는 학습이 얼마나 중요한 지를 느꼈습니다.

또한, 지금의 저에게 가장 중요한 것은 기본기를 탄탄히 다지는 것이라는 점을 다시 한번 느꼈습니다. 동시성 문제 해결, 가독성 있는 코드 작성, 그리고 AOP와 관련된 문제를 해결하는 과정에서, MSA나 Kafka와 같은 고급 기술을 다루기 전에 기본적인 원리와 기초 실력을 더욱 강화하는 것이 장기적인 성장에 필수적이라는 것을 깨달았습니다.

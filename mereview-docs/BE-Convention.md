# BE 컨벤션

### ✔네이밍 컨벤션
#### 모든 네이밍은 항상 모두가 알아볼 수 있게 작성

- **변수명은 카멜케이스로 작성**

 ```jsx
 //변수 
 private LocalDateTime createdTime;
 ```

 <br>

- **함수명은 카멜케이스로 작성**
  - 동사로 시작
  - 하나의 작업만 수행
  
```jsx
public int createMember(){
    //...내용
}
```

<br>

- **클래스명은 파스칼케이스로 작성**

```jsx
public class Member extends BaseEntity {
  //...내용
}
```
  
<br>

- **URL, 파일명은 케밥케이스로 작성**

```jsx
//URL
@GetMapping("/change-password")

//FILE
test-file
```

---


### ✔클래스 컨벤션

- **Entity Class**
  - BaseEntity 상속
  - Entity, Getter, NoArgsConstructor(access = PROTECTED) 이외의 어노테이션 사용 지양
  - Builder 패턴 활용
  - 값이 변경되는 비즈니스 로직은 Entity 클래스 안에서 수행

```jsx
@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Review extends BaseEntity {

    //...내용

    @Builder
    private Review(){
        //...내용
    }
}
```

<br>

# 여기는 좀더 다듬어서 정리할 예정
---

## 예외 처리

- 모든 예외에 대한 응답 처리는 ApiControllerAdvice 에서 처리한다. (try-catch-finally 구문을 사용하지 않고 그냥 예외를 발생시킬 것)
- 예외 응답 시 ApiResonse 클래스로 반환한다. (형식은 ApiControllerAdvice, ApiResponse 참조)

## DTO

- DTO 클래스에는 역시 Getter 와 NoArgsConstructor 를 사용한다.
- Builder 패턴 사용을 권장한다. (사실 이건 기억 잘 안남)
    - 

### 요청 객체

- 요청 객체는 아래와 같은 형식으로 명명하며, controller 와 service 로 구분한다.
- [도메인명][동작]Request
    
    ex) ReviewCreateRequest (Controller), ReviewCreateServiceRequest (Service)
    
- 요청 dto 에 대한 validation 은 controller 에서 수행한다. service 에서는 수행하지 않는다.
- Controller 요청에서 toServiceRequest, Service 요청 에서 toEntity 를 사용하여 변환작업을 수행한다. ??????? → 이부분 컨벤션 작성중 이해안감

### 응답객체

- Controller 에서 반환하는 모든 응답은 ApiResponse 클래스로 반환한다. (ApiResponse 참조)
- 응답 객체는 아래와 같은 형식으로 명명하며, 응답 종류에 따라 구분한다.
- [도메인명][구분]Response
    
    ex) ReviewResponse (전체조회 시), ReviewDetailResponse (상세조회 시)
    

## Service

- Service, RequiredArgsConstructor 어노테이션을 필히 작성한다. 작성하지 않는다면 개발자를 그만 두는 것을 권장한다.
- QueryService 와 Service 로 분리한다. CQRS 패턴 적용
    - QueryService 에는 Transactional(readOnly = true) 를 사용하고, Service 에는 그냥 Transactional 만 사용한다.
    - 역시 클래스에 Transactional 을 작성하지 않았다면 재입대를 권장한다.
- 인터페이스와 구현체로 나누지 않고 클래스만 작성한다.

## Repository

- 설명이 필요한가 싶다.
- Repository 어노테이션을 작성한다. 안해도 상관 없지 않냐? 라는 질문을 할 것이라면 본인의 지능을 15번 정도 의심하고 하길 바란다. 진짜 모르면 봐줌.
- Service 와 동일하게 CQRS 패턴을 적용하여 QueryRepository 와 Repository 로 구분한다.

---

<br>




### ✔패키지 기본 구조
```bash

Win10_64Bit_SSD 볼륨에 대한 폴더 경로의 목록입니다.
볼륨 일련 번호는 EC13-D5F4입니다.
C:.
│  MeReviewApplication.java
│  
├─api
│  ├─controller
│  │  ├─member
│  │  │  │  MemberController.java
│  │  │  │  
│  │  │  └─dto
│  │  │      └─request
│  │  │              MemberCreateRequest.java
│  │  │              
│  │  ├─movie
│  │  │  │  MovieController.java
│  │  │  │  
│  │  │  └─dto
│  │  │      └─request
│  │  │              MovieCreateRequest.java
│  │  │              
│  │  └─review
│  │      │  ReviewController.java
│  │      │  
│  │      └─dto
│  │          └─request
│  │                  ReviewCreateRequest.java
│  │                  
│  └─service
│      ├─member
│      │  │  MemberService.java
│      │  │  
│      │  └─dto
│      │      ├─request
│      │      │      MemberCreateServiceRequest.java
│      │      │      
│      │      └─response
│      │              MemberResponse.java
│      │              
│      ├─movie
│      │  │  MovieService.java
│      │  │  
│      │  └─dto
│      │      ├─request
│      │      │      MovieCreateServiceRequest.java
│      │      │      
│      │      └─response
│      │              MovieResponse.java
│      │              
│      └─review
│          │  ReviewService.java
│          │  
│          └─dto
│              ├─request
│              │      ReviewCreateServiceRequest.java
│              │      
│              └─response
│                      ReviewResponse.java
│                      
├─common
│  ├─config
│  │      AppConfig.java            // 뭐하는얘인지 적을 예정
│  │      JpaAuditingConfig.java    //
│  │      QueryDslConfig.java       //
│  │      SecurityConfig.java       //
│  │      WebConfig.java            //
│  │      
│  ├─response
│  │      ApiResponse.java          //
│  │      ResultPageResponse.java   //
│  │      
│  └─util
│      │  ApiControllerAdvice.java  //
│      │  SizeConstants.java        //
│      │  
│      ├─file
│      │      FileExtFilter.java    //
│      │      FileStore.java        //
│      │      UploadFile.java       //
│      │      
│      └─jwt
│              JwtAuthFilter.java   //
│              JwtUtils.java        //
│              
└─domain
    │  BaseEntity.java
    │  
    ├─member
    │  ├─entity
    │  │      Member.java
    │  │      
    │  └─repository
    │          MemberRepository.java
    │          
    ├─movie
    │  ├─entity
    │  │      Movie.java
    │  │      
    │  └─repository
    │          MovieRepository.java
    │          
    └─review
        ├─entity
        │      Review.java
        │      
        └─repository
                ReviewRepository.java
                
```
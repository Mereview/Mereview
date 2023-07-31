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

<br>


### ✔클래스, 인터페이스 컨벤션

- **Entity Class**
  - BaseEntity 상속
  - @Entity, @Getter, @NoArgsConstructor(access = PROTECTED) 어노테이션 사용
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

- **Dto class**
  - Getter, NoArgsConstructor 어노테이션 사용
  - Builder 패턴 사용
  - 값이 변경되는 비즈니스 로직은 Dto 클래스 안에서 수행

<br>

- **Request class**
  - 유효성 검사는 controller에서 수행
  - 명명규칙 : [도메인][동작][동작위치]Request

```jsx

```

<br>


- **Response class**
  - 모든 응답은 ApiResponse 클래스로 반환
  - 명명규칙 : [도메인][동작]Response

```jsx

```

<br>

- **Service class**
  - @Service, @RequiredArgsConstructor 어노테이션 사용
  - Service 클래스에는 @Transactional 추가
  - QueryService 클래스에는 @Transactional(readOnly = true) 추가
  - class 객체로 사용 (interface 사용 x)

```jsx

```

<br>

- **Repository interface**
  - @Repository 어노테이션 사용
  - QueryRepository와 Repository로 나눔

```jsx

```

---

<br>


###  ✔예외 처리

- **모든 예외 처리는 ApiControllerAdvice 에서 처리**
- **반환은 controller와 똑같이 ApiResponse 클래스 반환**

---

<br>


### ✔패키지 기본 구조
```bash
mereview:
│  MeReviewApplication.java
│  
├─api
│  ├─controller
│  │  ├─member
│  │  │  │  MemberController.java
│  │  │  │  
│  │  │  └─dto
│  │  │      └─request
│  │  │              
│  │  ├─movie
│  │  │  │  MovieController.java
│  │  │  │  
│  │  │  └─dto
│  │  │      └─request
│  │  │              
│  │  └─review
│  │      │  ReviewController.java
│  │      │  
│  │      └─dto
│  │          └─request
│  │                  
│  └─service
│      ├─member
│      │  │  MemberService.java
│      │  │  
│      │  └─dto
│      │      ├─request
│      │      │      
│      │      └─response
│      │              
│      ├─movie
│      │  │  MovieService.java
│      │  │  
│      │  └─dto
│      │      ├─request
│      │      │      
│      │      └─response
│      │              
│      └─review
│          │  ReviewService.java
│          │  
│          └─dto
│              ├─request
│              │      
│              └─response
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
    │  │      
    │  └─repository
    │          
    ├─movie
    │  ├─entity
    │  │      
    │  └─repository
    │          
    └─review
        ├─entity
        │      
        └─repository
                
                
```
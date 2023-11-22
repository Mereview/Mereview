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
public int createMovie(){
    //...내용
}
```

<br>

- **클래스명은 파스칼케이스로 작성**

```jsx
public class Movie extends BaseEntity {
  //...내용
}
```
  
<br>

- **파일명은 케밥케이스로 작성**

```jsx
//FILE
test-file
```

- **URL은 케밥케이스로 작성**
  - url마지막에 / 생략
  - 파일 확장자 x
  - 행위 포함 x
  - 명사 컨트롤 자원을 의미하는 경우 예외적으로 동사 사용

```jsx
@GetMapping("")     // 예시 작성 예정
@PostMapping("")    //
@PutMapping("")     //
@DeleteMapping("")  //
```

<br>

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
public class Movie extends BaseEntity {

    //...내용

    @Builder
    public Movie(...){
        //...내용
    }
}
```

<br>

- **Dto class**
  - @Getter, @NoArgsConstructor 어노테이션 사용
  - Builder 패턴 사용
  - 값이 변경되는 비즈니스 로직은 Dto 클래스 안에서 수행

<br>

- **Request class**
  - 명명규칙 : [도메인][동작][동작위치]Request

```jsx
//controller 에서 
@Getter
@NoArgsConstructor
public class MovieCreateRequest{
  
  //...내용

  @Builder
  public MovieCreateRequest(...){
    //...내용
  }
}
```
```jsx
//service 에서
@Getter
@NoArgsConstructor
public class MovieCreateServiceRequest{

  //...내용

  @Builder
  public MovieCreateServiceRequest(...){
    //...내용
  }
}
```

<br>


- **Response class**
  - 명명규칙 : [도메인][조건]Response
  - 페이지는 PageResponse 사용
  - 모든 응답은 ApiResponse 클래스로 반환

```jsx
//전체조회
@Getter
@NoArgsConstructor
public class MovieResponse{

  //...내용
}
```
```jsx
//상세조회
@Getter
@NoArgsConstructor
public class MovieDetailResponse{

  //...내용
}
```
```jsx
//반환 예시
@GetMapping("/")
public ApiResponse<PageResponse<List<MovieResponse>>> searchMovies(){
  // movie, page, api 세개의 response객체를 사용
}
```

<br>


- **Controller class**
  - @RestController, @RequestMapping, @RequiredArgsConstructor 어노테이션 사용
  - 유효성 검사는 여기서 실행
  
```jsx
@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

  //...내용
}
```

<br>


- **Service class**
  - @Service, @RequiredArgsConstructor 어노테이션 사용
  - Service 클래스에는 @Transactional 추가
  - QueryService 클래스에는 @Transactional(readOnly = true) 추가
  - class 객체로 사용 (interface 사용 x)

```jsx
@Service
@Transactional
@RequiredArgsConstructor
public class MovieService{

  //...내용
}
```
```jsx
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MovieQueryService{

  //...내용
}
```

<br>

- **Repository class, interface**
  - @Repository 어노테이션 사용
  - Repository는 interface 사용 JpaRepository 상속
  - QueryRepository는 class 사용 + @RequiredArgsConstructor 어노테이션 추가

```jsx
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{

}
```
```jsx
@Repository
@RequiredArgsConstructor
public class MovieQueryRepository{
  private final JPAQueryFactory queryFactory;
}
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
│  │      
│  ├─response
│  │      
│  └─util
│      │  
│      ├─file
│      │      
│      └─jwt
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
# 공통 컨벤션

### ✔Git Flow

- **메인 브랜치**
  - master : 제품으로 출시될 수 있는 브랜치
  - dev : 다음 출시 버전을 개발하는 브랜치
  - FEAT : 기능을 개발하는 브랜치

<br>

- **보조 브랜치** 
  - 그 외 모든 브랜치

<br>

---


### ✔Commit Convention

- 작업분류
  
| 작업코드 | 예시작업내용 |
|:---:|:---:|
| feat: | 새로운 기능 추가 |
| fix: | 버그 수정 또는 typo |
| refactor: | 리팩토링 |
| design: | CSS 등 사용자 UI 디자인 변경 |
| comment: | 필요한 주석 추가 및 변경 |
| style: | 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우 |
| test: | 테스트(테스트 코드 추가, 수정, 삭제, 비즈니스 로직에 변경이 없는 경우) |
| chore: | 위에 걸리지 않는 기타 변경사항 |
| init: | 프로젝트 초기 생성 |
| rename: | 파일 혹은 폴더명 수정하거나 옮기는 경우 |
| remove: | 파일을 삭제하는 작업만 수행하는 경우 |

<br>

- **git commit -m "[이슈번호]/[브랜치]/[작업분류]:[작업내용]”**
```jsx
git commit -m "S09P12C211-120/FEAT-MOVIE/feat:영화 Entity 개발"
```

<br>

---


### ✔MR(Merge Request) Convention

- **MR/[이슈번호]/[branch]/[작업분류]:[작업내용]**
```jsx
MR/S09P12C211-120/FEAT-MOVIE/feat:영화 Entity 개발
```

<br>

---


### ✔Jira Convention

- **에픽 이슈타입**
  - 큰 이슈
  - 스토리 포인트 부여 X

<br>

- **스토리 이슈타입**
  - 중간 이슈
  - 스토리 포인트 부여 X
  - 다음으로 분할 - 작업 등록

<br>

- **작업 이슈타입**
  - 작은 이슈
  - 스토리 포인트 부여 0~4 시간
  - 다음에서 분할 - 스토리 등록

<br>

- **하위 작업 사용하지말라는 요청 있었음**

<br>

- **사용하면 유용한 필터**

| 필터 이름 | JQL |
|:---:|:---:|
| FE 컴포넌트 | issuetype in (Bug, Story, Epic, Task) AND component = FE |
| BE 컴포넌트 | issuetype in (Bug, Story, Epic, Task) AND component = BE |
| 작업 이슈타입 | issuetype = Task | 
| 스토리 이슈타입 | issuetype = Story | 
| 나or미할당 이슈만 | issuetype in (Bug, Story, Epic, Task) AND assignee in (currentUser(), EMPTY) |
| 최근 업데이트됨 | updatedDate >= -3d |
| 이번주 작업 | project = S09P12C211 AND issuetype = Task AND reporter in (currentUser()) And created >= startOfWeek() AND created <= endOfWeek() order by created DESC |


<br>


### ✔참조문서

 - [FE 컨벤션](./FE-Convention.md)
 - [BE 컨벤션](./BE-Convention.md)
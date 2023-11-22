# FE 컨벤션

- 이름 규칙
    - 변수명 및 함수명은 카멜 케이스를 사용한다.
    - 파일명 및 컴포넌트명에는 파스칼 케이스를 사용한다.
    - 총합 → total + 변수명 ex. totalAmount
- 들여쓰기는 2칸으로 한다.
- 컴포넌트
    - 컴포넌트와 함수는 화살표 함수형으로 작성한다.(rsc)
    
    ```jsx
    import React from "react";
    import "./App.css";
    
    function NameBox = (props) => {
      const name = "test";
      return <div>{name}</div>;
    }
    
    export default NameBox;
    ```
    
- 함수
    - 에로우펑션을 사용한다.
- 따옴표
    - 객체의 키는 큰따옴표를 사용한다.
    
    ```jsx
    // bad
    var my_object = {
      key: "value",
    };
    
    // good
    var my_object = {
      "key": "value",
      "key2": "value2",
    };
    ```
    
    - 문자열은 작은 따옴표를 사용한다.
    - 문자열 내의 따옴표는 백슬래시(’\’)를 사용한다.
    
    ```jsx
    str = 'I\'m String.'
    ```
    
- 서버 요청시 URL 변수 만들어 사용하기
    - 변수 형식은 const로 한다.
    - URL 변수는 모두 대문자로 한다.  ex)  USER_INFO_URL
- 리덕스 Store
    - 각각의 리듀서 이름은 목적에맞게 소문자로 작성하고, 내보낸다.
- 주석은 기능 단위로 한줄정도로 간략하게 작성한다.
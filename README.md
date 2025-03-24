# 💡 일정관리 앱 기능
  ### JavaDoc
  준비중

  ### API 명세서 
  https://documenter.getpostman.com/view/43364760/2sAYkHpJV6
  
  ### 사용자 인터페이스 방식
   - Postman을 통한 HTTP 동신
   - 데이터 형태 : JSON
     
  ### 입력
   - 정해진 기능에 맞는 HTTP METHOD, Params, Body(json) 값들

  ### 출력
  - 기능에 알맞는 Json 출력값들
  - 200 번대 : 성공
  - 400 번대 : 입력값 오류로 인한 실패

  ### 기능
  - 할 일 등록
  - 할 일 내용 및 작성자 수정
  - 조건에 맞는 모든 할 일 출력
  - 아이디 값에 맞는 하나의 할 일 출력
  - 아이디 값에 맞는 하나의 할 일 삭제
  - 사용자의 이메일/ 작성자 이름 수정

  ### 특수 상황에 대한 예외 처리
  - 정수입력인지 확인하기
  - 선택하는 상황이 왔을때 가능한 선택지의 바운더리를 상황에 따라 조절하고 입력값 검증하기

---

### ⚙️ 개발 환경
`JAVA`
`JDK 17.0.14`
`MYSQL`

### 🕰️ 개발 기간
25.03.24 -

---

# 📚 설계

## UML
1차본이라 수정될 수 있음.
![image](https://github.com/user-attachments/assets/ed983ad1-cbeb-4124-8208-f3243cb65530)



## 초기 구조도
1차본이라 수정될 수 있음.
![image](https://github.com/user-attachments/assets/f75e1c74-dd4f-4f77-ad37-5ad6906ca731)

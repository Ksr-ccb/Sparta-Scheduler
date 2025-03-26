# 💡 일정관리 앱 기능
  ### API 명세서 
  https://documenter.getpostman.com/view/43364760/2sAYkHpJV6
  
  ### 사용자 인터페이스 방식
   - Postman을 통한 HTTP 통신
   - 데이터 형태 : JSON
     
  ### 입력
   - 정해진 기능에 맞는 HTTP METHOD, Params, Body(json) 값들

  ### 출력
  - 기능에 알맞는 Json 출력값들
  - HTTP Status Code
      - 200 번대 : 200(+반환 JSON) , 201(+ 반환 JSON) , 204( 반환값 x )
      - 400 번대 : 400(변수는 왔는데 값 형태가 이상하거나 비밀번호 틀렸을 때), 404(id값들이 잘못됐을 때)
      - 500 번대 : 500(요청이 올바르긴한데 쿼리 실행 도중에 문제가 있음 - db연결이나 코드로직 문제)

  ### 기능
  ```
  [할 일]
  일정의 고유 식별자(ID), 할일, 작성자 아이디, 비밀번호, 작성/수정일을 저장

  - 할 일 등록
      - 필요한 값을 입력해줘야 함
      - 필수 입력 값 : 할일, 작성자 아이디, 비밀번호
      - 작성/수정 일은 할 일 등록시 자동 갱신  +  최초 입력 시, 수정일은 작성일과 동일

  - 할 일 내용 및 작성자 수정
      - 작성할 값을 입력해야 함
      - 수정 가능한 값 :  할일, 작성자명 만 수정 가능
      - 비밀번호 필수 입력  +  비밀번호가 맞지 않으면 삭제 불가능
      - 수정일 은 수정 완료 시, 수정한 시점으로 자동 갱신

  - 조건에 맞는 모든 할 일 출력
      - 유효한 조건 값들만 입력가능
      - 유효 값 : 수정일 (YYYY-MM-DD) , 작성자명
            - 조건 중 한 가지만을 충족하거나, 둘 다 충족을 하지 않을 수도, 두 가지를 모두 충족할 수도 있음
      - `수정일` 기준 내림차순 정렬

  - 아이디 값에 맞는 하나의 할 일 출력
      - 스케줄 테이블에 있는 아이디값이어야 함

  - 아이디 값에 맞는 하나의 할 일 삭제
      - 스케줄 테이블에 있는 아이디 값이어야 함
      - 비밀번호는 필수 입력 +  비밀번호가 맞지 않으면 삭제 불가능

  - 페이지 넘버와 사이즈에 맞는 모든 할 일 출력
      - 페이지 사이즈는 디폴트값으로 10을 가진다.
      - 전달받은 페이지 번호와 크기를 기준으로 쿼리를 작성하여 필요한 데이터만을 조회하고 반환
            - 범위를 넘어선 페이지를 요청하는 경우 빈 배열을 반환

  ```
```    
  [작성자]
  작성자의 고유 식별자(ID), 이름, 이메일, 등록일, 수정일 정보를 가지고 있음.

  - 작성자 정보 추가
      - 담당자의 이메일 정보가 형식에 맞는지 확인 
      - 동일한 데이터로 작성자 추가 방지
```
  ### 예외 발생 처리
  - 선택한 일정 정보를 조회할 수 없을 때 예외가 발생
      - 잘못된 정보로 조회하려고 할 때 => Bad Requset
      - 이미 삭제된 정보를 조회하려고 할 때 => Not Found
  - 필수 값이 없을 때 예외가 발생
      - null 체크 후 빈값일 때 => Not Found
  - 중복 사용자 등록 예외
      - 사용자 등록할 때 이미 등록되어 있는 `이름`,`이메일` 세트가 들어오면 가입 방지 => Bad Request 
---

### ⚙️ 개발 환경
`JAVA`
`JDK 17.0.14`
`MySQL`

### 🕰️ 개발 기간
25.03.24 - 25.03.26

---

# 📚 설계
## 과제 요구사항과 구조도
![image](https://github.com/user-attachments/assets/b9ce50b1-e06a-4404-ac89-bf8ed8b53dde)



## UML
![image](https://github.com/user-attachments/assets/b96a6287-614e-422d-86d9-1f0c6338aea2)



## ERD 
![image](https://github.com/user-attachments/assets/81c75ba6-ab42-46b3-895c-684ad7301cd3)



---
  # 깃 컨벤션 
참고 :   https://treasurebear.tistory.com/70


🎨::
코드의 구조/형태 개선
Improve structure / format of the code.

⚡️::
성능 개선
Improve performance.

🔥::
코드/파일 삭제
Remove code or files.

🐛::
버그 수정
Fix a bug.

✨::
새 기능
Introduce new features.

📝::
문서 추가/수정
Add or update documentation.

💄::
UI/스타일 파일 추가/수정
Add or update the UI and style files.

♻️::
코드 리팩토링
Refactor code.

➕::
의존성 추가
Add a dependency.

➖::
의존성 제거
Remove a dependency.

🔨::
개발 스크립트 추가/수정
Add or update development scripts.

💡::
주석 추가/수정
Add or update comments in source code.

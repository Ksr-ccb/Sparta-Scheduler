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

  ### 기능
  - 할 일 등록
      - 필요한 값을 입력해줘야 함
  - 할 일 내용 및 작성자 수정
      - 작성할 값을 입력해야 함
      - 비밀번호 필수 입력
  - 조건에 맞는 모든 할 일 출력
      - 유효한 조건 값들만 입력가능
  - 페이지 넘버와 사이즈에 맞는 모든 할 일 출력
      - 페이지 사이즈는 디폴트값으로 10을 가진다. 
  - 아이디 값에 맞는 하나의 할 일 출력
      - 스케줄 테이블에 있는 아이디값이어야 함
  - 아이디 값에 맞는 하나의 할 일 삭제
      - 스케줄 테이블에 있는 아이디 값이어야 함
      - 비밀번호는 필수 입력
      - 비밀번호가 맞지 않으면 삭제 불가능
  - 작성자 정보 추가
      - 담당자의 이메일 정보가 형식에 맞는지 확인 

  ### 예외 발생 처리
  - 선택한 일정 정보를 조회할 수 없을 때 예외가 발생
      - 잘못된 정보로 조회하려고 할 때 => Bad Requset
      - 이미 삭제된 정보를 조회하려고 할 때 => Not Found
  - 필수 값이 없을 때 예외가 발생
      - null 체크 후 빈값일 때 => Not Found
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
![image](https://github.com/user-attachments/assets/d3c76d78-9537-4e39-8961-3be9420df122)





## UML
![image](https://github.com/user-attachments/assets/5ff1b303-e301-43dc-81b3-756b5f8fbc48)




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

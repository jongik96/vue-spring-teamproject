- 공공데이터를 활용한 국내 부동산 거래정보 검색 서비스, 주변 시설정보 제공
    
    ( 주변 주택정보, 환경정보, 상권정보 활용 )
    

## Project Info.

- 🙍‍♂️🙎‍♂️ 개발인원 : 2인
- 📆 개발기간 : 8일 (2021.05.20 ~ 2021.05.27 )
    - 5/20 : 전체 계획 설정
    - 5/21 : DB 정보 수집 및 생성
    - 5/22 ~ 5/26 : 백엔드 및 프론트엔드 구현
    - 5/27 : 테스트
- **Github [코드보기](https://github.com/jongik96/Housing-Transaction-Information-Service)**

## Tech Stack.

- Front-end : jsp, vue.js, jQuery
- Back-end : Java, Springboot
- Database : MySQL
- Cooperation : git, gitlab

## 주요 기능 소개

### 1. 메인화면

- 상단의 메뉴바에서 메뉴 선택, 로그인 및 회원가입, 아파트 이름으로 검색하기 등이 가능.
- 중앙 부분에서는 시/도, 구/군, 동/읍 을 선택해 지역별 주택거래정보 조회가 가능, 해당 동/읍 을 각자의 계정의 관심목록에 추가 가능

![https://user-images.githubusercontent.com/68894097/130561802-3b87623e-8777-4466-aebc-4a28804d4bfa.png](https://user-images.githubusercontent.com/68894097/130561802-3b87623e-8777-4466-aebc-4a28804d4bfa.png)

### 2. 로그인

- 세션을 활용한 로그인 기능 구현

![https://user-images.githubusercontent.com/68894097/130561814-e665ad79-9e03-4d10-a333-ef3cb38d0acd.png](https://user-images.githubusercontent.com/68894097/130561814-e665ad79-9e03-4d10-a333-ef3cb38d0acd.png)

### 3. 지역별 거래정보 검색

- select box 선택을 통한 검색 DB에 저장된 주택의 위도와 경도 값을 이용해 GoogleMap에 마커 표시
- 

![https://user-images.githubusercontent.com/68894097/130561850-e6cc7119-d395-4588-aa5e-8d243aed1c6d.png](https://user-images.githubusercontent.com/68894097/130561850-e6cc7119-d395-4588-aa5e-8d243aed1c6d.png)

### 4. 아파트 명을 통한 거래정보 검색

- 문자열 검색을 통해 해당하는 문자를 가진 아파트를 조회
- 

![https://user-images.githubusercontent.com/68894097/130561858-45e8e56d-b9f6-435e-b6d0-f87073cc0ee9.png](https://user-images.githubusercontent.com/68894097/130561858-45e8e56d-b9f6-435e-b6d0-f87073cc0ee9.png)

### 5. 주변 정보 표시

- 공공데이터 API를 활용해 반경 200m 안에 있는 주변 상권, 공공기관 등의 위치를 GoogleMap을 통해 표시

![https://user-images.githubusercontent.com/68894097/130561602-e3e7c7ca-52cb-4da3-b0a6-831258796ea5.png](https://user-images.githubusercontent.com/68894097/130561602-e3e7c7ca-52cb-4da3-b0a6-831258796ea5.png)

### 6. 오늘의 뉴스

- vue.js를 이용, Naver news 검색 api를 활용해 실시간 뉴스 조회
- 

![https://user-images.githubusercontent.com/68894097/130561976-ca3ce422-9444-48e8-8d33-6d9e5270bf52.png](https://user-images.githubusercontent.com/68894097/130561976-ca3ce422-9444-48e8-8d33-6d9e5270bf52.png)

## Project Review.

- 초기 기획, 설계가 중요하다!
    - 중간중간에 DB설계를 변경하게 되는 경우가 꽤 있었는데 개발하다가 다시 설계 과정으로 돌아가는 일이 생겼었다..
- 단순히 프로젝트 전체의 설계, 기획도 중요하지만 폴더, 파일의 구조들도 어떻게 정리할 것인지를 미리 정해 놓는 것이 좋을 것 같다.

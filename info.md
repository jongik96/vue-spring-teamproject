본 프로젝트는 삼성청년SW아카데미 5기 교육 과정 중 진행된 프로젝트입니다.

## Project Info.

- 개발기간 : 2021.06.28 ~ 2021.08.13 (약 7주)
- 개발인원
    - Front-end (My position) : 2명, Back-end: 2명 (총4명)
    - git을 통한 협업은 ssafy에서 관리하는 서버의 gitlab을 활용.

## Tech Stack

- Front-End **(My Position)**
    - Bootstrap
    - Vue.js - Vuex
- Back-end
    - Java
    - Springboot
- Database
    - MariaDB
- Cooperation
    - git, gitlab
    - jira

## Infra Structure

![](https://images.velog.io/images/pji3504/post/ef6c7de6-6367-4d38-9dc2-97bf16b7217a/image.png)

## 주요 기능

### 검색기능

- 문제/알고리즘/유저 별로 검색 지원
- 필터를 통해 원하는 조건 설정 가능

![](https://images.velog.io/images/pji3504/post/249ec165-6c95-409a-b7fc-79fd91ecc987/image.png)

### 글 작성

- Toast api 사용하여 마크다운 양식으로 글 작성 가능

![](https://images.velog.io/images/pji3504/post/a163d932-6d88-4b9c-bb74-52ed1975206d/image.png)

### 풀이요청 기능

- 웹소켓을 사용, 문제풀이를 요청 받았을 때, 요청에 대한 답변이 등록되었을 때 알림 기능

![](https://images.velog.io/images/pji3504/post/d638931d-5a82-4cec-a7fa-c75932c93a08/image.png)
### 팔로우 기능

- 유저/알고리즘/문제사이트에 대한 팔로우를 통해 타임라인에서 조회 가능

![](https://images.velog.io/images/pji3504/post/cae079f4-4df0-4e61-a48c-d1afa46bb810/image.png)
## Project Review

### 가독성 좋은 코드 짜기, 컴포넌트 분해!

- 오른쪽은 LoginForm.vue 코드 중 일부분 🤦‍♂️
- 하나의 script안에서 페이지를 구현하면서 처음엔 느끼지 못 했지만
    
    프로젝트를 완성하고 나서 코드를 리뷰해보니 가독성도 떨어졌고 유지보수하기에 어려울 것 같다는 것을 몸소 느꼈다..
    
- 여러가지 컴포넌트 분해 방법들의 장단점을 알아보고 필요한 것을 적절할 때에 사용하는 습관을 길러야겠다

![](https://images.velog.io/images/pji3504/post/04e26d2c-9748-4c0d-bb29-134f9f99a5d9/image.png)

### 디자인, 반응형웹

- 직접 디자인을 담당하면서 CSS에 대한 기초가 부족하다는 것을 많이 느꼈다.
- Bootstrap 등의 CSS 프레임워크에 의존하지 않고도 직접 UI를 디자인 할 수 있는 능력을 길러야겠다.
- 메인페이지 (Timeline.vue) 를 제외하고는 반응형웹을 대부분 적용하지 못 했다.
    
    아마 미숙한 CSS 실력으로 인해 시간을 많이 뺏긴 이유가 젤 컸을 것이라 생각한다. 
    
    조금 더 사용자 편의성을 고려한 디자인을 할 수 있도록 추가적인 학습이 필요하다.

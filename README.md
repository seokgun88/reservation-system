# 네이버 예약 서비스 만들기
이 프로젝트는 기본적인 웹프로그래밍 지식으로 Front-End에서 Back-End까지 전문화된 개발을 한다.


# YG 코딩 컨밴션
## [FE]
- 변수 명은 CamelCase
- 모듈 이름은 자바 클래스명 처럼 (Class)
- 함수 이름은 fucntion name() { }
- 엘리먼트 변수 선언 예) var $btnAdmin = $('.btn_admin');
- “” (double quotes)
- [] : [] (JSON)
- tab: space 두칸

## [BE]
- MVC 패턴을 철저히
- 유효성 검사는 필수
- 메서드는 동사 시작
- 컨트롤러, 서비스 : get / add / modify / remove

## [Git]
- C_YG : 마스터 브랜치
- task{n} : n번째 태스크 브랜치
- checkout C_YG; merge task{n}; : 머지 방법 (다 같이 있을 때)

## [그라운드룰]
- 페어 프로그래밍!
- 배고프면 밥먹고 하자.
- 서로 대화를 많이 하고 이해하자.
- 기한은 준수하자. 미루지 말자.
- 좋은 부분은 아낌없이 칭찬하자.
- 일과 시작 전에 미팅을 합시다. 자신의 상태를 이야기 하자.
- 일과 종료 전에 미팅을 하면서 서로 진행상황을 보고 합시다.
- 재촉하지 않기..... 느려도 이해해주세요.


# REST API
## Product
| Title                 | URL                             | Method | URL params   | Data params | Response |
|-----------------------|---------------------------------|--------|--------------|-------------|----------|
| 프로모션 목록         | /api/products/promotions        | GET    |              |             | { [ { id=[int], name=[str], description=[str], placeName=[str], mainImageId=[int] }, ... ] } |
| 상품 상세 정보        | /api/products/:id               | GET    | id=[integer] |             | { nema=[str], images=[[int], ...], description=[str], event=[str], content=[str], subImage=[int], placeName=[str], placeLot=[str], placeStreet=[str], tel=[str], homepage=[str], email=[str], reviewCount=[int], reviewTotalScore=[int] } |
| 상품 예약 정보        | /api/proudcts/:id/reservation   | GET    | id=[integer] |             | { name=[str], placeName=[str], displayStart=[Date], displayEnd=[Date], observationTime=[str], mainImageId=[int] } |
| 상품 리뷰 보기   | /api/products/:id/reviews?limit=:limit&page=:page | GET    | id=[integer], limit=[integer], page=[integer] |             | { [ { review=[str], score=[int], modifyDate=[Date], userEmail=[str] }, ... ] } |  

## Category  
 | Title              | URL                                     | Method | URL parms                    |  Data params | Response |
 |--------------------|-----------------------------------------|--------|------------------------------|--------------|----------|
 | 카테고리 목록      | /api/categories                         | GET    |                              |              | { [ { id=[int], name=[str], productCount=[int] }, ... ] } |
 | 카테고리 상품 목록 | /api/categories/:id/products?page=:page | GET    | id=[integer], page=[integer] |              | { [ { id=[int], name=[str], description=[str], placeName=[str], mainImageId=[int] }, ... ] } |  

## Reservation
| Title        | URL                  | Method | URL parms |  Data params | Response |
|--------------|----------------------|--------|-----------|--------------|----------|
| 예약 등록    | /api/reservations    | POST   |           |              |          |
| 내 예약 보기 | /api/reservations/my | GET    |           |              |          |  


## Review
| Title                 | URL                     | Method | URL parms    |  Data params | Response |
|-----------------------|-------------------------|--------|--------------|--------------|----------|
| 리뷰 등록             | /api/reviews            | POST   |              |              |          |


## Image
| Title          | URL         | Method | URL parms |  Data params | Response |
|----------------|-------------|--------|-----------|--------------|----------|
| 리뷰 사진 등록 | /api/images | POST   |           |              |          |


## User
| Title   | URL           | Method | URL parms |  Data params | Response |
|---------|---------------|--------|-----------|--------------|----------|
| 내 정보 | /api/users/my | GET    |           |              |          | |

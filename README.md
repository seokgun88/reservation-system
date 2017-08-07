# 네이버 예약 서비스 만들기
이 프로젝트는 기본적인 웹프로그래밍 지식으로 Front-End에서 Back-End까지 전문화된 개발을 한다.

# REST API
## Product
| Title                 | URL                             | Method | URL params   | Data params | Response |
|-----------------------|---------------------------------|--------|--------------|-------------|----------|
| 프로모션 목록         | /api/products/promotions        | GET    |              |             |          |
| 상품 상세 정보        | /api/products/:id               | GET    | id=[integer] |             |          |
| 상품 상세 설명 이미지 | /api/products/:id/detailedImage | GET    | id=[integer] |             |          |
| 상품 예약 정보        | /api/proudcts/:id/reservation   | GET    | id=[integer] |             |          |
| 상품 리뷰 전체 보기   | /api/products/:id/reviews       | GET    | id=[integer] |             |          |
| 상품명                | /api/products/:id/name          | GET    | id=[integer] |             |          |  

 ## Category  
 | Title              | URL                                     | Method | URL parms                    |  Data params | Response |
 |--------------------|-----------------------------------------|--------|------------------------------|--------------|----------|
 | 카테고리 목록      | /api/categories                         | GET    |                              |              |          |
 | 카테고리 상품 목록 | /api/categories/:id/products?page=:page | GET    | id=[integer], page=[integer] |              |          |  

## Reservation
| Title        | URL                  | Method | URL parms |  Data params | Response |
|--------------|----------------------|--------|-----------|--------------|----------|
| 예약 등록    | /api/reservations    | POST   |           |              |          |
| 내 예약 보기 | /api/reservations/my | GET    |           |              |          |  


## Review
| Title                 | URL                     | Method | URL parms    |  Data params | Response |
|-----------------------|-------------------------|--------|--------------|--------------|----------|
| 리뷰 등록             | /api/reviews            | POST   |              |              |          |
| 해당 리뷰 이미지 목록 | /api/reviews/:id/images | GET    | id=[integer] |              |          |


## Image
| Title          | URL         | Method | URL parms |  Data params | Response |
|----------------|-------------|--------|-----------|--------------|----------|
| 리뷰 사진 등록 | /api/images | POST   |           |              |          |


## User
| Title   | URL           | Method | URL parms |  Data params | Response |
|---------|---------------|--------|-----------|--------------|----------|
| 내 정보 | /api/users/my | GET    |           |              |          | |

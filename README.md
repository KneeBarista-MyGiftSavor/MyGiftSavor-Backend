## 🎁 소개

내가 가진 기프티콘들을 한 곳에 모아 관리할 수 있는 MyGiftSavor의 백엔드입니다.

---

## 🎁 ER-diagram

![hacklearn (2)](https://user-images.githubusercontent.com/52443695/129303004-184be889-c000-413d-88ce-b902671a67d4.png)

🔗 [링크](https://dbdiagram.io/d/6111f6992ecb310fc3c46735)

---

## 🎁 API 명세서

🔗 [링크](https://documenter.getpostman.com/view/14407018/Tzz5tJSC)

---

## 🎁 프로젝트 구조

```jsx
+---main
|   +---generated
|   +---java
|   |   \---hackalearn
|   |       \---mygiftsavor
|   |           |   MyGiftSavorApplication.java
|   |           |
|   |           +---infra
|   |           |   +---config
|   |           |   |       OpenEntityManagerConfig.java
|   |           |   |       WebSecurityConfig.java
|   |           |   |
|   |           |   +---exception
|   |           |   |       AccessDeniedException.java
|   |           |   |       CustomRuntimeException.java
|   |           |   |       DuplicateException.java
|   |           |   |       GlobalExceptionHandler.java
|   |           |   |       InvalidReqBodyException.java
|   |           |   |       NoSuchDataException.java
|   |           |   |       S3Exception.java
|   |           |   |
|   |           |   \---jwt
|   |           |           JwtAuthenticationFilter.java
|   |           |           JwtTokenProvider.java
|   |           |
|   |           \---module
|   |               +---controller
|   |               |       GifticonController.java
|   |               |       HomeController.java
|   |               |       UserController.java
|   |               |
|   |               +---model
|   |               |   +---domain
|   |               |   |       Gifticon.java
|   |               |   |       User.java
|   |               |   |       UserSource.java
|   |               |   |
|   |               |   \---dto
|   |               |           GifticonDtos.java
|   |               |           JwtDto.java
|   |               |           UserDtos.java
|   |               |
|   |               +---repository
|   |               |       GifticonRepository.java
|   |               |       UserRepository.java
|   |               |
|   |               \---service
|   |                       CustomUserDetailService.java
|   |                       GifticonService.java
|   |                       HomeService.java
|   |                       S3Service.java
|   |                       UserService.java
|   |
|   \---resources
|       |   application.yml
|       |
|       +---static
|       \---templates
\---test
    \---java
        \---hackalearn
            \---mygiftsavor
                |   MyGiftSavorApplicationTests.java
                |
                \---module
                    +---controller
                    |       UserControllerTest.java
                    |
                    \---service
                            GifticonServiceTest.java
                            HomeServiceTest.java
```

---

## 🎁 기술 스택

- Spring Boot
    - Spring Security
    - JPA
- MySQL
- AWS
    - S3
    - RDS
    - EC2

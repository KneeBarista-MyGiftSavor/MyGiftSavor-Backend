## π μκ°

λ΄κ° κ°μ§ κΈ°νν°μ½λ€μ ν κ³³μ λͺ¨μ κ΄λ¦¬ν  μ μλ MyGiftSavorμ λ°±μλμλλ€.

---

## π ER-diagram

![hacklearn (2)](https://user-images.githubusercontent.com/52443695/129303004-184be889-c000-413d-88ce-b902671a67d4.png)

π [λ§ν¬](https://dbdiagram.io/d/6111f6992ecb310fc3c46735)

---

## π API λͺμΈμ

π [λ§ν¬](https://documenter.getpostman.com/view/14407018/Tzz5tJSC)

---

## π νλ‘μ νΈ κ΅¬μ‘°

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

## π κΈ°μ  μ€ν

- Spring Boot
    - Spring Security
    - JPA
- MySQL
- AWS
    - S3
    - RDS
    - EC2

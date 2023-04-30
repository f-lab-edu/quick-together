
## 프로젝트 소개
**Quick Together 는 모임을 만들거나 자기와 맞는 모임에 들어가 빠르게 미팅 일정을 잡을 수 있는 프로젝트입니다.**

직접 모임을 만들고 **웹에디터를 이용하여 다양하게 꾸밀 수 있습니다.**

모임에 입장신청을 하거나, 초대되어 들어가서 **채팅을 통하여 자유롭게 의사소통**을 할 수 있습니다.

또한, 미팅 일정을 예약할 때 구성원들의 일정 정보를 이용하여 자동으로 **미팅 일정을 추천**을 해줍니다.

## 기술스택
#### 프론트엔드
<img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white">
<img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">
<img src="https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white">

#### 백엔드
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/JPA-59666C?style=for-the-badge&logo=#59666C&logoColor=white"/>
<img src="https://img.shields.io/badge/mariaDB-003545?style=for-the-badge&logo=mariaDB&logoColor=white">
<img src="https://img.shields.io/badge/websocket-010101?style=for-the-badge&logo=socket.io&logoColor=white">
<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">


#### 인프라
<img src="https://img.shields.io/badge/aws-232F3E?style=for-the-badge&logo=aws&logoColor=white"> <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white"/>
<img src="https://img.shields.io/badge/apache tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=white">
<img src="https://img.shields.io/badge/linux-FCC624?style=for-the-badge&logo=linux&logoColor=black">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=Jenkins&logoColor=white">

## ci/cd 구성도
<img width="745" alt="image" src="https://user-images.githubusercontent.com/41093183/231686691-5fdb947b-716c-46fa-b6e4-70ceffa29a8c.png">

## 기능
**내 프로젝트**

![all](https://user-images.githubusercontent.com/41093183/233784743-0e821bbc-2b3b-4f27-986f-0b838a5baf74.gif)


</br>

**프로젝트 생성**

![createProject](https://user-images.githubusercontent.com/41093183/233788871-5d3d6818-8b1b-4b5d-a890-441a8d721900.gif)

</br>

**채팅 기능**

![chat](https://user-images.githubusercontent.com/41093183/233783541-2ea37d7b-e58e-47d9-bedf-a61b4f8fd031.gif)

</br>

**초대 기능**

![invite](https://user-images.githubusercontent.com/41093183/233783778-1d998e37-0088-405d-b148-587d66684acd.gif)


## 주요 이슈
* 부하테스트를 통하여 로그인 성능 이슈 해결 (https://hong-good.tistory.com/2)
  - Artillery를 통해 Latency 측정 및 쓰레드 덤프를 통해 어떤 클래스가 쓰레드를 점유하고 있는지 확인
  - 해시 연산  횟수를 줄여서 성능 향상

* JPA에서 발생하는 N+1 문제 해결 (https://hong-good.tistory.com/3)
  - 모임 상세조회 과정 중 N+1 문제가 발생했고, fetch join으로 문제 해결
  - 다중 컬렉션 fetch join시 발생하는 문제 해결

* 알람 기능 추상화 (https://hong-good.tistory.com/4)
  - FCM을 이용하여 알람 기능 추가
  - FCM 플랫폼에서 카카오 플랫폼으로 바뀔 경우 유연한 변경이 가능하게 추상화






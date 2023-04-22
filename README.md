
## 프로젝트 소개
**Quick Together 는 모임을 만들거나 자기와 맞는 모임에 들어가 빠르게 미팅 일정을 잡을 수 있는 프로젝트입니다.**

직접 모임을 만들고 **웹에디터를 이용하여 다양하게 꾸밀 수 있습니다.**

모임에 입장신청을 하거나, 초대되어 들어가서 **채팅을 통하여 자유롭게 의사소통**을 할 수 있습니다.

또한, 미팅 일정을 예약할 때 구성원들의 일정 정보를 이용하여 자동으로 **미팅 일정을 추천**을 해줍니다.

## 기능
**내 프로젝트**

![all](https://user-images.githubusercontent.com/41093183/233784743-0e821bbc-2b3b-4f27-986f-0b838a5baf74.gif)


**채팅 기능**

![chat](https://user-images.githubusercontent.com/41093183/233783541-2ea37d7b-e58e-47d9-bedf-a61b4f8fd031.gif)

**초대 기능**

![invite](https://user-images.githubusercontent.com/41093183/233783778-1d998e37-0088-405d-b148-587d66684acd.gif)


## ci/cd 구성도
<img width="745" alt="image" src="https://user-images.githubusercontent.com/41093183/231686691-5fdb947b-716c-46fa-b6e4-70ceffa29a8c.png">


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






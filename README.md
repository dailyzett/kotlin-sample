# security-keypad
보안 키패드 구현해보기

실제 서비스 회사에서 많이 사용하는 보안 키패드를 리액트 / 스프링 + 코틀린 서버로 간단하게 구현해봅니다.

<img width="1585" alt="스크린샷 2024-11-23 오후 3 35 32" src="https://github.com/user-attachments/assets/9736434c-6117-4b2f-8f8a-ec6a0616f94d">

사진 출처: https://www.nshc.net/home/mobile-security/openwebnfilter/

# 동작 방식

<img width="389" alt="스크린샷 2024-11-25 오전 12 02 05" src="https://github.com/user-attachments/assets/f50094ba-b46f-4bd4-a8e7-e3d40ceab763">

1. 클라이언트에서 가상 키패드 요청 버튼을 누르는 즉시, 서버는 키패드 ID를 UUID 로 발급합니다.
2. 그 키패드에 대한 공개키, 개인키를 발급합니다.
3. 서버에서 가상 키패드가 담긴 문자열을 클라이언트에게 응답으로 내려줍니다.

현재 서버에서는 키패드 ID 에 대한 개인키를 메모리에 저장하고 있기 때문에,
서버를 종료했다가 다시 키면 키 매칭이 안되기 때문에 복호화가 진행되지 않습니다.

<img width="384" alt="스크린샷 2024-11-25 오전 12 05 39" src="https://github.com/user-attachments/assets/79d8e390-f2c3-4f0b-b11a-1f0508a0ee21">

5 버튼을 눌렀을 때 크롬 콘솔 창에서 공개키로 암호화된 키 출력과 서버에서 개인 키를 가지고 복호화된 키가 출력되는 것을 확인할 수 있습니다.

<img width="910" alt="스크린샷 2024-11-25 오전 12 06 24" src="https://github.com/user-attachments/assets/5f83222e-4f73-4897-a732-973bb061802f">

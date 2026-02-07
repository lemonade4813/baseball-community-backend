
 🖥️ 프로젝트 소개
===================

야구 커뮤니티 사이트 백엔드 개발

💻 배포사항

- 네이버 클라우드 jar 파일 배포[http://118.67.143.164:8080/]

- 무중단 배포 설정
```txt
sudo vi /etc/systemd/system/baseball-community.service
```
```txt
[Unit]
Description=Baseball Community Backend
After=network.target

[Service]
ExecStart=/usr/bin/java -jar /root/baseball-community-backend-0.0.1-SNAPSHOT.jar
WorkingDirectory=/root
Restart=always
User=root
StandardOutput=append:/var/log/baseball-community.log
StandardError=append:/var/log/baseball-community-error.log

[Install]
WantedBy=multi-user.target
```
```txt
sudo systemctl start baseball-community
```

🛠️ 사용기술
- Spring Boot 3.4.1
- Spring Security 6.4.2
- Lombok
- MongoDB

🔨 구현사항(2025.2.23 현재)

- 회원가입 및 로그인 구현(회원가입 시 아이디 및 닉네임 중복체크 & 비밀번호 암호화)
- 경기 일정 / 게시물 작성 페이지 CRUD 구현
- 스프링 시큐리티로 액세스 토큰(JWT) 생성
- 로그인 정보를 커스텀한 userDetails에 저장, 아이디 및 닉네임은 필요 메서드에서 @AuthenticationPrincipal 활용하여 사용.
- STOMP 활용하여 채팅 기능 구현



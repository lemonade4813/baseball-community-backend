package com.example.baseballcommunitybackend.controller;

import com.example.baseballcommunitybackend.document.User;
import com.example.baseballcommunitybackend.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.nio.file.*;
import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final String uploadDir = "uploads";

    public UserController( UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestParam("userId") String userId,
            @RequestParam("team") String team,
            @RequestParam("password") String password,
            @RequestParam("nickname") String nickname,
            @RequestParam("profileImage") MultipartFile profileImage) {


        try {

            Path uploadPath = Paths.get(uploadDir);

            // 폴더가 없으면 생성
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 이미지 저장
            String fileName = userId + "_" + profileImage.getOriginalFilename();
            Path imagePath = Paths.get(uploadDir).resolve(fileName);
            Files.copy(profileImage.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

            // 사용자 저장
            User user = new User();
            user.setUserId(userId);
            user.setTeam(team);
            user.setNickname(nickname);
            user.setPassword(password);
            user.setProfileImagePath("/users/image/" + fileName);
            userService.registerUser(user);

            return ResponseEntity.ok("성공적으로 회원가입을 완료했습니다.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 업로드에 실패하였습니다.");
        }
    }

    @GetMapping("/image/{fileName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            return ResponseEntity.ok().body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam("userId") String userId, @RequestParam("password") String password) {
        boolean isAuthenticated = userService.authenticateUser(userId, password);
        if (!isAuthenticated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 잘못되었습니다.");
        }
        return ResponseEntity.ok("로그인에 성공하였습니다.");
    }

}
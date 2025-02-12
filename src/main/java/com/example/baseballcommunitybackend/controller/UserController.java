package com.example.baseballcommunitybackend.controller;

import com.example.baseballcommunitybackend.document.User;
import com.example.baseballcommunitybackend.dto.LoginRequestDTO;
import com.example.baseballcommunitybackend.dto.LoginResponseDTO;
import com.example.baseballcommunitybackend.service.CustomUserDetails;
import com.example.baseballcommunitybackend.service.CustomUserDetailsService;
import com.example.baseballcommunitybackend.service.UserService;
import com.example.baseballcommunitybackend.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    private final String uploadDir = "uploads";

    public UserController( UserService userService,
                           AuthenticationManager authenticationManager,
                           JwtUtil jwtUtil
                         ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam("team") String team,
            @RequestParam("password") String password,
            @RequestParam("nickname") String nickname,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) {

        try {
            Path uploadPath = Paths.get(uploadDir);

            // 폴더가 없으면 생성
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = null;
            if (profileImage != null && !profileImage.isEmpty()) {
                // 이미지 저장
                fileName = userId + "_" + profileImage.getOriginalFilename();
                Path imagePath = uploadPath.resolve(fileName);
                Files.copy(profileImage.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
            }

            // 사용자 저장
            User user = new User();
            user.setUserId(userId);
            user.setTeam(team);
            user.setNickname(nickname);
            user.setPassword(password);
            if (fileName != null) {
                user.setProfileImagePath("/users/image/" + fileName);
            }
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
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginDto) {

        String userId = loginDto.getUserId();
        String Password = loginDto.getPassword();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userId, Password)
            );
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();

            String token = jwtUtil.generateToken(userId);
            System.out.println(token);

            LoginResponseDTO response = new LoginResponseDTO(
                    token,
                    user.getNickname(),
                    user.getProfileImagePath(),
                    user.getTeam()
            );
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 사용자가 존재하지 않습니다.");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 올바르지 않습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 처리 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return ResponseEntity.ok("성공적으로 로그아웃 되었습니다.");
    }

}

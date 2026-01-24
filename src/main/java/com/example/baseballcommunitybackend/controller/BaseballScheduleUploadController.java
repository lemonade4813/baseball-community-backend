package com.example.baseballcommunitybackend.controller;

import com.example.baseballcommunitybackend.document.Schedule;
import com.example.baseballcommunitybackend.repository.ScheduleRepository;
import com.example.baseballcommunitybackend.service.BaseballScheduleUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor // 생성자 주입을 자동으로 처리 (final 필드 대상)
public class BaseballScheduleUploadController {

    private final BaseballScheduleUploadService baseballScheduleUploadService;
    private final ScheduleRepository scheduleRepository;

    @PostMapping("/upload")
    public ResponseEntity<?> BaseballScheduleUploadController(@RequestParam("file") MultipartFile file) {
        // 1. 파일이 비어있는지 체크
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("파일이 존재하지 않습니다.");
        }
        try {
            // 2. 엑셀 파싱 서비스 호출
            List<Schedule> schedules = baseballScheduleUploadService.parseBaseballExcel(file.getInputStream());

            // 3. MongoDB에 벌크 저장 (한 번에 insert)
            scheduleRepository.saveAll(schedules);

            return ResponseEntity.ok(schedules.size() + "건의 경기 일정이 성공적으로 저장되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("데이터 처리 중 오류 발생: " + e.getMessage());
        }
    }
}

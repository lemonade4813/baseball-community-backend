package com.example.baseballcommunitybackend.controller;

import com.example.baseballcommunitybackend.document.Stadium;
import com.example.baseballcommunitybackend.service.StadiumInfoService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/stadium")
public class StadiumInfoController {

    private final StadiumInfoService stadiumInfoService;

    StadiumInfoController(StadiumInfoService stadiumInfoService) {
        this.stadiumInfoService = stadiumInfoService;
    }

    private final String uploadDir = "uploads/stadium";

    @GetMapping
    public ResponseEntity<List<Stadium>> getStadiumList() {
        List<Stadium> stadiumInfoList = stadiumInfoService.findAllStaudiumInfo();
        return ResponseEntity.ok(stadiumInfoList);
    }

    @GetMapping("/{team}")
    public ResponseEntity<Stadium> getStationByTeam(@PathVariable String team) {
        Stadium stadiumInfo = stadiumInfoService.findStaudiumInfoByTeam(team);
        return ResponseEntity.ok(stadiumInfo);
    }

    @GetMapping("/image/{fileName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            System.out.println(resource);
            return ResponseEntity.ok().body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{team}")
    public ResponseEntity<Stadium> updateStadium(@PathVariable String team, @RequestBody Stadium stadium) {
        // 서비스 계층에서 team 이름을 기준으로 데이터를 업데이트하거나 저장하는 로직 호출
        // 보통 MongoDB의 경우 save() 또는 findAndModify 등을 사용합니다.
        Stadium updatedStadium = stadiumInfoService.saveOrUpdateStadium(team, stadium);
        return ResponseEntity.ok(updatedStadium);
    }




}

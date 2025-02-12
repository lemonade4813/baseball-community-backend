package com.example.baseballcommunitybackend.controller;


import com.example.baseballcommunitybackend.document.Post;
import com.example.baseballcommunitybackend.document.Stadium;
import com.example.baseballcommunitybackend.service.StadiumInfoService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<Stadium>> getStadiumInfo() {
        List<Stadium> stadiumInfoList = stadiumInfoService.findAllStaudiumInfo();

        return ResponseEntity.ok(stadiumInfoList);
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




}

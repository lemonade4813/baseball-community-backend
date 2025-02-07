package com.example.baseballcommunitybackend.controller;


import com.example.baseballcommunitybackend.document.Post;
import com.example.baseballcommunitybackend.document.Stadium;
import com.example.baseballcommunitybackend.service.StadiumInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stadium")
public class StadiumInfoController {

    private final StadiumInfoService stadiumInfoService;

    StadiumInfoController(StadiumInfoService stadiumInfoService) {
        this.stadiumInfoService = stadiumInfoService;
    }

    @GetMapping
    public ResponseEntity<List<Stadium>> getStadiumInfo() {
        List<Stadium> stadiumInfoList = stadiumInfoService.findAllStaudiumInfo();

        System.out.println(stadiumInfoList);


        if (stadiumInfoList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(stadiumInfoList);
    }






}

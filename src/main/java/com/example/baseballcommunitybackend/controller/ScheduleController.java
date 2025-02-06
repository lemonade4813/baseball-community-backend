package com.example.baseballcommunitybackend.controller;


import com.example.baseballcommunitybackend.document.Schedule;
import com.example.baseballcommunitybackend.dto.ErrorDTO;
import com.example.baseballcommunitybackend.dto.ResponseDTO;
import com.example.baseballcommunitybackend.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;


    @GetMapping("/schedule")
    public ResponseEntity<?> scheduleInfo(){


        try{
            List<Schedule> ScheduleList = scheduleService.findAllSchedules();
            ResponseDTO<List<Schedule>> response = ResponseDTO.<List<Schedule>>builder().data(ScheduleList).build();
            return ResponseEntity.ok().body(response);
        }
        catch (Exception e){
            ErrorDTO error = ErrorDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

        }

    }


}

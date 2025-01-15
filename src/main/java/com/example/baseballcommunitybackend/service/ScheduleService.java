package com.example.baseballcommunitybackend.service;

import com.example.baseballcommunitybackend.document.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Schedule> findAllSchedules() {
        return mongoTemplate.findAll(Schedule.class);
    }

}

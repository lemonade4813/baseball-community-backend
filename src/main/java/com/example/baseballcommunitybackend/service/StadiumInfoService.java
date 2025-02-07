package com.example.baseballcommunitybackend.service;


import com.example.baseballcommunitybackend.document.Stadium;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StadiumInfoService {
    private final MongoTemplate mongoTemplate;

    StadiumInfoService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    public List<Stadium> findAllStaudiumInfo(){

        return mongoTemplate.findAll(Stadium.class);
    }
}

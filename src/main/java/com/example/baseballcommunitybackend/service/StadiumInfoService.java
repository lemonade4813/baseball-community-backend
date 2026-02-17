package com.example.baseballcommunitybackend.service;


import com.example.baseballcommunitybackend.document.Stadium;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

    public Stadium findStaudiumInfoByTeam(String team){
        Query query = new Query(Criteria.where("team").is(team));
        return mongoTemplate.findOne(query, Stadium.class);
    }
}

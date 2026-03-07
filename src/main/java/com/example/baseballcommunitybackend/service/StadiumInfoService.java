package com.example.baseballcommunitybackend.service;


import com.example.baseballcommunitybackend.document.Stadium;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
    public Stadium saveOrUpdateStadium(String team, Stadium stadium) {
        // 1. 조건 설정 (어떤 데이터를 바꿀 것인가)
        Query query = new Query(Criteria.where("team").is(team));

        // 2. 업데이트할 내용 설정 (무엇을 바꿀 것인가)
        Update update = new Update()
                .set("stadiumName", stadium.getStadiumName())
                .set("name", stadium.getName())
                .set("address", stadium.getAddress())
                .set("seat", stadium.getSeat())
                .set("area", stadium.getArea())
                .set("direction", stadium.getDirection())
                .set("features", stadium.getFeatures()) // 배열 업데이트
                .set("imagePath", stadium.getImagePath())
                .set("homepage", stadium.getHomepage())
                .set("coordinates", stadium.getCoordinates());

        // 3. Upsert 실행 (조건에 맞는 게 있으면 업데이트, 없으면 신규 생성)
        // FindAndModifyOptions를 사용하여 업데이트된 후의 객체를 바로 반환받을 수 있습니다.
        mongoTemplate.upsert(query, update, Stadium.class);

        // 업데이트된 결과를 다시 조회해서 반환 (또는 그냥 stadium 객체 반환)
        return findStaudiumInfoByTeam(team);
    }
}

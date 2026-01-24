package com.example.baseballcommunitybackend.repository;

import com.example.baseballcommunitybackend.document.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScheduleRepository extends MongoRepository<Schedule, String> {
    // 기본 CRUD 메서드가 자동으로 생성
}
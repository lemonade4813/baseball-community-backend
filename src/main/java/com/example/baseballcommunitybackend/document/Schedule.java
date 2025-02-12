package com.example.baseballcommunitybackend.document;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "schedules")
public class Schedule {
    private String month;
    private String date;
    private String day;
    private String time;
    private String awayTeam;
    private String homeTeam;
    private String stadium;
    private String notes;
}

package com.example.baseballcommunitybackend.document;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stadiums")
@Data

public class Stadium {

    private String team;
    private String stadiumName;
    private String address;
    private Integer seat;
    private Integer area;
    private String[] features;
    private String imagePath;
    private String homepage;
    private Float[] coordinates;

}

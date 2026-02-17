package com.example.baseballcommunitybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamCountDTO {
    private String id;
    private String totalCount;
}

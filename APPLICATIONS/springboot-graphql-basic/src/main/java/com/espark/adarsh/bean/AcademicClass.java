package com.espark.adarsh.bean;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AcademicClass {

    UUID id;
    Teacher teacher;
    LocalDateTime startAt;
    LocalDateTime endsAt;
    Difficulty difficulty;
}

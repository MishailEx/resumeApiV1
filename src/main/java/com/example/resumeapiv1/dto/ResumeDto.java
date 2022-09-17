package com.example.resumeapiv1.dto;

import lombok.Data;

@Data
public class ResumeDto {

    private String description;

    public ResumeDto() {
    }

    public ResumeDto(String description) {
        this.description = description;
    }
}

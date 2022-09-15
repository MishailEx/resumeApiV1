package com.example.resumeapiv1.utill;

import lombok.Data;

@Data
public class PersonErrorResponse {

    private String massage;

    public PersonErrorResponse(String massage) {
        this.massage = massage;
    }
}

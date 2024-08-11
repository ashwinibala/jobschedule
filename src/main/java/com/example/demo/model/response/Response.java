package com.example.demo.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Response {
    private Integer code;
    private String message;
    private String messageBody;
}

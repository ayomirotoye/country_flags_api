package com.demo.country_flag_api.models;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean error;
    private String msg;
    private T data;
}

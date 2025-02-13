package com.demo.country_flag_api.models;

import java.util.List;

public record ResponseWrapper<T>(String next, String previous, List<T> dataList) {
    public static <T> ResponseWrapper<T> empty() {
        return new ResponseWrapper<T>("", "", List.of());
    }

    public static <T> ResponseWrapper<T> of(List<T> dataList) {
        return new ResponseWrapper<>("", "", dataList);
    }
}

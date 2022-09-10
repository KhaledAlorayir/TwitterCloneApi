package com.example.twitterapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@AllArgsConstructor
@Data
public class Pagination<T> {
    private List<T> results;
    private int current_page;
    private int page_count;
}

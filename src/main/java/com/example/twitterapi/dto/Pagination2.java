package com.example.twitterapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Pagination2<T> {
    private List<T> results;
    private boolean has_next;
    private boolean has_prev;
    private int page_number;
}

package org.example.models.dto;

import lombok.Data;

import java.util.List;
@Data
public class PageDTO<T> {
    private Long total;
    private Long pages;
    private List<T> list;
}

package com.example.javapostgresql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Long id;
    private String bookName;
    private int pageNumbers;
    private char sex;
    private Topic topic;
    private LocalDate releaseDate;
    private Long authorId;
}

package com.example.javapostgresql.model;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Book {
    private Long id;
    private String bookName;
    private int pageNumbers;
    private char sex;
    private Topic topic;
    private LocalDate releaseDate;
    private Long authorId;
}

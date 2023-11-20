package com.example.javapostgresql.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Author {
    private Long id;
    private String authorName;
    private char sex;
}

package com.example.U5W2D4.authors;


import com.example.U5W2D4.blogs.Blog;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String surname;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    private LocalDate birthday;
    private String avatarUrl;
    @OneToMany(mappedBy = "author")
    @Getter(AccessLevel.NONE)
    @JsonIgnore
    private List<Blog> blogList;
}

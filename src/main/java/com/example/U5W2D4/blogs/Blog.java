package com.example.U5W2D4.blogs;

import com.example.U5W2D4.authors.Author;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blogs")
public class Blog {
    @Id
    @GeneratedValue
    private UUID id;
    private String category;
    private String title;
    private String coverUrl;
    private String content;
    private int readingTime;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public Blog(String category, String title, String coverUrl, String content, int readingTime, Author author) {
        this.category = category;
        this.title = title;
        this.coverUrl = coverUrl;
        this.content = content;
        this.readingTime = readingTime;
        this.author = author;
    }
}

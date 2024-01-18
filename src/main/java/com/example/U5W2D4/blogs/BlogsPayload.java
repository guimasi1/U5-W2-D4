package com.example.U5W2D4.blogs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BlogsPayload {
    private String category;
    private String title;
    private String coverUrl;
    private String content;
    private int readingTime;
    private String authorURL;
}

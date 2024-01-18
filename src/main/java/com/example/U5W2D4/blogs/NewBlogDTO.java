package com.example.U5W2D4.blogs;

import lombok.AllArgsConstructor;
import lombok.Getter;

public record NewBlogDTO (String category, String title, String coverUrl, String content, int readingTime, String authorURL) {
}

package com.example.U5W2D4.blogs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BlogsDAO extends JpaRepository<Blog, UUID> {
    Optional<Blog> findByTitle(String title);

    Page<Blog> findByCategory(String category, Pageable pageable);

    Page<Blog> findByReadingTime(int readingTime, Pageable pageable);

    Page<Blog> findByReadingTimeAndCategory(int readingTime, String category, Pageable pageable);
}

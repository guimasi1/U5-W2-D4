package com.example.U5W2D4.blogs;

import com.example.U5W2D4.authors.Author;
import com.example.U5W2D4.authors.AuthorsService;
import com.example.U5W2D4.exceptions.BadRequestException;
import com.example.U5W2D4.exceptions.NotFoundException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Getter
@Service
public class BlogsService {
    @Autowired
    private BlogsDAO blogsDAO;

    @Autowired
    private AuthorsService authorsService;

    public Page<Blog> getBlogs(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return blogsDAO.findAll(pageable);
    }

    public Page<Blog> getByCategory(String category, int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return blogsDAO.findByCategory(category, pageable);
    }

    public Page<Blog> getByReadingTime(int readingTime, int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size,Sort.by(orderBy));
        return blogsDAO.findByReadingTime(readingTime, pageable);
    }
    public Page<Blog> getByReadingTimeAndCategory(int readingTime, String category, int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size,Sort.by(orderBy));
        return blogsDAO.findByReadingTimeAndCategory(readingTime,category,pageable);
    }
    public Blog save(NewBlogDTO newBlogDTO) {
        blogsDAO.findByTitle(newBlogDTO.title()).ifPresent(author1 -> {
            throw new BadRequestException("Il titolo " + newBlogDTO.title() + " è già in uso");
        });
        Author author = authorsService.findById(newBlogDTO.authorURL());
        String category = newBlogDTO.category();
        String title = newBlogDTO.title();
        String coverUrl = newBlogDTO.coverUrl();
        String content = newBlogDTO.content();
        int readingTime = newBlogDTO.readingTime();
        Blog blogToSave = new Blog(category,title,coverUrl,content,readingTime,author);
        return blogsDAO.save(blogToSave);
    }

    public Blog findById(UUID uuid) {
        return blogsDAO.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
    }

    public Blog findByIdAndUpdate(UUID uuid, NewBlogDTO newBlogDTO) {
        Blog blog = this.findById(uuid);
        Author author = authorsService.findById(newBlogDTO.authorURL());
        blog.setAuthor(author);
        blog.setCategory(newBlogDTO.category());
        blog.setContent(newBlogDTO.category());
        blog.setTitle(newBlogDTO.title());
        blog.setCoverUrl(newBlogDTO.coverUrl());
        blog.setReadingTime(newBlogDTO.readingTime());
        return blogsDAO.save(blog);
    }

    public void deleteById(UUID uuid) {
        Blog found = this.findById(uuid);
        blogsDAO.delete(found);
    }



}

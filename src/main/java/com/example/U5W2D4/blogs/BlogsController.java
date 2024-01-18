package com.example.U5W2D4.blogs;

import com.example.U5W2D4.authors.AuthorsResponseDTO;
import com.example.U5W2D4.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/blogs")
public class BlogsController {
    @Autowired
    private BlogsService blogsService;

    @GetMapping
    public Page<Blog> getAllBlogs(@RequestParam(required = false)  String category,
                            @RequestParam(defaultValue = "0")  int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(defaultValue = "id") String orderBy) {
        if(category != null) return blogsService.getByCategory(category,page,size,orderBy);
        return blogsService.getBlogs(page, size, orderBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorsResponseDTO create(@RequestBody @Validated NewBlogDTO body, BindingResult validation) {
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Ci sono errori nel payload");
        } else {
            Blog newBlog = blogsService.save(body);
            return new AuthorsResponseDTO(newBlog.getId());

        }
    }

    @GetMapping("/{uuid}")
    public Blog getBlogById(@PathVariable UUID uuid) {
        return blogsService.findById(uuid);
    }

    @PutMapping("/{uuid}")
    public Blog updateBlogById(@PathVariable UUID uuid, @RequestBody NewBlogDTO newBlogDTO) {
        return blogsService.findByIdAndUpdate(uuid, newBlogDTO);
    }

    @DeleteMapping("/{uuid}")
    public void deleteById(@PathVariable UUID uuid) {
        blogsService.deleteById(uuid);
    }
}

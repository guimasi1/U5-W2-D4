package com.example.U5W2D4.authors;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.U5W2D4.exceptions.BadRequestException;
import com.example.U5W2D4.exceptions.NotFoundException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;


@Service
@Getter
public class AuthorsService {
    @Autowired
    AuthorsDAO authorsDAO;

    @Autowired
    Cloudinary cloudinary;

    public Page<Author> getAuthors(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return authorsDAO.findAll(pageable);
    }

    public Author save(NewAuthorDTO author) {
        Random rnd = new Random();
        authorsDAO.findByEmail(author.email()).ifPresent(author1 -> {
            throw new BadRequestException("Email " + author.email() + " già in uso");
        });
        LocalDate date = LocalDate.of(rnd.nextInt(1940,2010),rnd.nextInt(1,13), rnd.nextInt(1,29));

        Author newAuthor = new Author();
        newAuthor.setSurname(author.surname());
        newAuthor.setName(author.name());
        newAuthor.setBirthday(date);
        newAuthor.setEmail(author.email());
        newAuthor.setAvatarUrl("https://ui-avatars.com/api/?name=" + author.name() + "+" + author.surname());
        return authorsDAO.save(newAuthor);
    }


    public Author findById(UUID id) {
        return authorsDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Author findByIdAndUpdate(UUID uuid, Author body) {
        Author found = this.findById(uuid);
        found.setName(body.getName());
        found.setSurname(body.getSurname());
        found.setBirthday(body.getBirthday());
        found.setAvatarUrl(body.getAvatarUrl());
        return authorsDAO.save(found);
    }

    public void deleteById(UUID uuid) {
        Author found = this.findById(uuid);
        authorsDAO.delete(found);
    }



    public String uploadPicture(MultipartFile file) throws IOException {

        String url = (String) cloudinary.uploader()
                .upload(file.getBytes(), ObjectUtils.emptyMap())
                .get("url");
        return url;
    }

}

package com.example.U5W2D4.authors;

import com.example.U5W2D4.exceptions.BadRequestException;
import com.example.U5W2D4.exceptions.NotFoundException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;


@Service
@Getter
public class AuthorsService {
    @Autowired
    AuthorsDAO authorsDAO;

    public Page<Author> getAuthors(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return authorsDAO.findAll(pageable);
    }

    public Author save(Author author) {
        Random rnd = new Random();
        authorsDAO.findByEmail(author.getEmail()).ifPresent(author1 -> {
            throw new BadRequestException("Email " + author.getEmail() + " già in uso");
        });
        author.setAvatarUrl("https://ui-avatars.com/api/?name=" + author.getName() + "+" + author.getSurname());
        author.setBirthday(LocalDate.of(rnd.nextInt(1940,2010),rnd.nextInt(1,13), rnd.nextInt(1,29)));
        return authorsDAO.save(author);
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




}

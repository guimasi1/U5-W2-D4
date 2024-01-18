package com.example.U5W2D4.authors;

import com.example.U5W2D4.config.MailgunSender;
import com.example.U5W2D4.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/authors")
public class AuthorsController {
    @Autowired
    private AuthorsService authorsService;

    @Autowired
    private MailgunSender mailgunSender;

    @GetMapping
    public Page<Author> getAuthors(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "id") String id) {
        return authorsService.getAuthors(page,size,id);
    }

    @GetMapping("/{uuid}")
    public Author getAuthorById(@PathVariable UUID uuid) {
        return authorsService.findById(uuid);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorsResponseDTO create(@RequestBody @Validated NewAuthorDTO author, BindingResult validation) {
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Ci sono errori nel payload");
        } else {
            Author newAuthor = authorsService.save(author);
            mailgunSender.sendMail(newAuthor.getEmail());
            return new AuthorsResponseDTO(newAuthor.getId());
        }
    }

    @PutMapping("/{uuid}")
    public Author updateById(@PathVariable UUID uuid, @RequestBody Author body) {
        return authorsService.findByIdAndUpdate(uuid, body);
    }

    @DeleteMapping("/{uuid}")
    public void deleteById(@PathVariable UUID uuid) {
        authorsService.deleteById(uuid);
    }

    @PostMapping("/{uuid}/upload")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile file, @PathVariable UUID uuid) throws IOException {
        // "avatar" deve corrispondere esattamente alla chiave del Multipart dove sarà contenuto il file, altrimenti non troveremo il file
        return authorsService.uploadPicture(file);
    }



}

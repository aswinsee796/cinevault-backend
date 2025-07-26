package com.aswin.myapp.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.aswin.myapp.entity.Movies;
import com.aswin.myapp.entity.User;
import com.aswin.myapp.service.Movieservice;
import com.aswin.myapp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController
@RequestMapping("/Movies")
public class Moviecontroller {

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    @Autowired
    private Movieservice movser;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getmov")
    public List<Movies> getDetails() {
        return movser.getAllDetails();
    }

//     Accept username in JSON body, fetch User, and save Movie
    @PostMapping("/{username}/addmov")
    public ResponseEntity<?> postDetails(@PathVariable String username, @RequestBody Movies m) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        m.setUser(userOpt.get());  // attach the User entity to Movies
        Movies savedMovie = movser.SaveDetails(m); // existing method
        return ResponseEntity.ok(savedMovie);
    }



    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file,
                                              @RequestParam("username") String username) {
        try {
            Path userDir = Paths.get(UPLOAD_DIR, username);
            Files.createDirectories(userDir);

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = userDir.resolve(filename);

            Files.write(filePath, file.getBytes());

            String imageUrl = "http://localhost:8080/Movies/uploads/" + username + "/" + filename;
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

    @GetMapping("/uploads/{username}/{filename:.+}")
    public ResponseEntity<byte[]> serveImage(@PathVariable String username, @PathVariable String filename) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR, username, filename);
            byte[] imageBytes = Files.readAllBytes(filePath);

            return ResponseEntity.ok()
                    .header("Content-Type", Files.probeContentType(filePath))
                    .body(imageBytes);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable long id) {
        movser.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public Movies update(@PathVariable long id, @RequestBody Movies m) {
        return movser.update(id, m);
    }

    @GetMapping("/getmov/{id}")
    public Optional<Movies> getById(@PathVariable long id) {
        return movser.getById(id);
    }

    @GetMapping("/user/{username}")
    public List<Movies> getMoviesByUsername(@PathVariable String username) {
        return movser.getMoviesByUsername(username);
    }
}

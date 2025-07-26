package com.aswin.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.aswin.myapp.entity.Movies;
import com.aswin.myapp.entity.User;

import java.util.List;

public interface Movierepo extends JpaRepository<Movies, Long> {
    // Fetch all movies for a given user
    List<Movies> findByUser(User user);

    // Alternatively, if you want to fetch by username directly
    List<Movies> findByUserUsername(String username);
}

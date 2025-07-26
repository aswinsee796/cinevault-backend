package com.aswin.myapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aswin.myapp.entity.Movies;
import com.aswin.myapp.entity.User;
import com.aswin.myapp.repository.Movierepo;

@Service
public class Movieservice {
    @Autowired
    private Movierepo movrep;

    // post
    public Movies SaveDetails(Movies M) {
        return movrep.save(M);
    }

    // get
    public List<Movies> getAllDetails() {
        return movrep.findAll();
    }

    // get by id 
    public Optional<Movies> getById(long Movie_id) {
        return movrep.findById(Movie_id);
    }

    // delete
    public void deleteById(long Movie_id) {
        movrep.deleteById(Movie_id);
    }

    // update
    public Movies update(long Movie_id, Movies M) {
        return movrep.save(M);
    }

    // new: get movies by User object
    public List<Movies> getMoviesByUser(User user) {
        return movrep.findByUser(user);
    }

    // new: get movies by username directly
    public List<Movies> getMoviesByUsername(String username) {
        return movrep.findByUserUsername(username);
    }
}

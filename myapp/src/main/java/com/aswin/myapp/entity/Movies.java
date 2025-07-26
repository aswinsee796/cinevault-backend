package com.aswin.myapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "Letterboard3")
public class Movies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private long id;

    @Column(name = "movie_name")
    private String movieName;

    @Column(name = "rating")
    private String rating;

    @Column(name = "review")
    private String review;

    @Column(name = "month")
    private String month;

    @Column(name = "image_url")
    private String imageUrl;

    // New: Link movie to a user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    // Constructors
    public Movies() {}

    public Movies(long id, String movieName, String rating, String review, String month, String imageUrl, User user) {
        this.id = id;
        this.movieName = movieName;
        this.rating = rating;
        this.review = review;
        this.month = month;
        this.imageUrl = imageUrl;
        this.user = user;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getMovieName() { return movieName; }
    public void setMovieName(String movieName) { this.movieName = movieName; }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}

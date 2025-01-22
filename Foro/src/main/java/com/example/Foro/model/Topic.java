package com.example.Foro.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Topic() {
        this.createdAt = new Date();
    }

    // Getters y setters
}

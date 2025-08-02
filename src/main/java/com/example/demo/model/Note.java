package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table
public class Note {

    @Id
    private long noteId;
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;
    private String ownerName;
    private String ownerEmail;

}

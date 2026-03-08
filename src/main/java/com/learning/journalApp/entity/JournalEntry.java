package com.learning.journalApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Entity
@Data
@Table(name = "journal_entry")
public class JournalEntry {
    @Id
    private String id;

    private String title;

    private String content;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
//    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;

}

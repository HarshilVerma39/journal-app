package com.learning.journalApp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    private String id;

    @Column(unique = true)
    @NonNull
    private String userName;

    @NonNull
    private String password;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<JournalEntry> journalEntries = new ArrayList<>();

}

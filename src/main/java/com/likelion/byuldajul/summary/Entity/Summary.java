package com.likelion.byuldajul.summary.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Summary")
public class Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate date;

    @Column (columnDefinition = "TEXT")
    private String content;

    @Column
    private String email;

    public Summary(String email, String content, LocalDate date) {
        this.email = email;
        this.content = content;
        this.date = date;
    }

}
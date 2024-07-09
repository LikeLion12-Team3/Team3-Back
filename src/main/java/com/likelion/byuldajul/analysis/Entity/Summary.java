package com.likelion.byuldajul.analysis.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

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
    private LocalDateTime date;

    @Column (columnDefinition = "TEXT")
    private String content;

    @Column
    private String email;

    public Summary(String content, LocalDateTime date) {
        this.content = content;
        this.date = date;
    }

}
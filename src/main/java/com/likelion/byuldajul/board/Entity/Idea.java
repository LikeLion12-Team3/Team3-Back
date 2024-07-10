package com.likelion.byuldajul.board.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "ideas")
public class Idea extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idea_id")
    private Long id;

    private String title;

    private String mainText;

    @Builder
    public Idea(String title, String mainText) {
        this.title = title;
        this.mainText = mainText;
    }

    public void update(String title, String mainText) {
        this.title = title;
        this.mainText = mainText;
    }



}

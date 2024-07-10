package com.likelion.byuldajul.diary.Entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "hashtag")
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;

    public void updateHashtag(String name) {
        this.name = name;
    }

}

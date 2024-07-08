package com.likelion.byuldajul.board.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "hashtags")
public class Hashtag extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;

    private String name;

//    @OneToMany(mappedBy = "hashtag")
//    private List<DiaryHashtag> diaryHashtags;

    @Builder
    public  Hashtag(String name) {
//        this.id = id;
        this.name = name;
    }









}

package com.likelion.byuldajul.board.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "diarys")
public class Diary extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long Id;

    private String title;

    private String template;

    private String mainText;

    private String impression;

    @OneToMany(mappedBy = "diary")
    private List<DiaryHashtag> diaryHashtags = new ArrayList<>();

    @Builder
    public Diary(String title, String template, String mainText, String impression, List<DiaryHashtag> diary_hashtags){
//        super.createdAt =createdAt;
        this.title = title;
        this.template = template;
        this.mainText = mainText;
        this.impression = impression;
//        this.diaryHashtags = diary_hashtags;
    }




}

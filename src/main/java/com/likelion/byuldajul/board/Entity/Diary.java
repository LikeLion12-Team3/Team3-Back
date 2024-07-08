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

    private String remark;

    private String plan;

    @Builder
    public Diary(String title, String template, String mainText, String impression, String remark, String plan){
        this.title = title;
        this.template = template;
        this.mainText = mainText;
        this.impression = impression;
        this.remark = remark;
        this.plan = plan;
    }





}

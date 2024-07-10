package com.likelion.byuldajul.commit.entity;

import com.likelion.byuldajul.diary.Entity.Diary;
import com.likelion.byuldajul.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "commits")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Commit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email", referencedColumnName = "email")
    private User user;


    @Builder
    public Commit(Long id, LocalDate date, String title, Diary diary, User user) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.diary = diary;
        this.user = user;
    }
}

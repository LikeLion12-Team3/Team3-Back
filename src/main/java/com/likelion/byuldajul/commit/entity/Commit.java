package com.likelion.byuldajul.commit.entity;

import com.likelion.byuldajul.board.Entity.Diary;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "commits")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Commit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @Builder
    public Commit(Long id, LocalDateTime createdAt, String title, Diary diary) {
        this.id = id;
        this.createdAt = createdAt;
        this.title = title;
        this.diary = diary;
    }
}

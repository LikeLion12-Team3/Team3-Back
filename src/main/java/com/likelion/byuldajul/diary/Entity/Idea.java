package com.likelion.byuldajul.diary.Entity;

import com.likelion.byuldajul.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ideas")
public class Idea extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idea_id")
    private Long id;

    private String title;


    @Column(columnDefinition = "TEXT")
    private String  mainText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email", referencedColumnName = "email")
    private User user;


    public void update(String title, String mainText) {
        this.title = title;
        this.mainText = mainText;
    }



}

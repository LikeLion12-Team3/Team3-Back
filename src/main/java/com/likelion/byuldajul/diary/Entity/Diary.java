package com.likelion.byuldajul.diary.Entity;

import com.likelion.byuldajul.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "diary")
public class Diary extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String template;

    @Column(columnDefinition = "TEXT")
    private String mainText;

    @Column(columnDefinition = "TEXT")
    private String impression;

    private String remark;

    private String plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email", referencedColumnName = "email")
    private User user;

    @OneToMany(mappedBy = "diary", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hashtag> hashTags;

    public void update(String title, String template, String mainText, String impression, String remark, String plan){
        this.title = title;
        this.template = template;
        this.mainText = mainText;
        this.impression = impression;
        this.remark = remark;
        this.plan = plan;
    }

    public void setHashTags(List<Hashtag> hashTags) {
        this.hashTags = hashTags;
    }
}

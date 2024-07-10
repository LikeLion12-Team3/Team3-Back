package com.likelion.byuldajul.diary.Repository;


import com.likelion.byuldajul.diary.Entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findAllByUser_Email(String email);

    @Query("SELECT DISTINCT d FROM Diary d JOIN d.hashTags h JOIN d.user u " +
            "WHERE u.email = :email AND LOWER(h.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Diary> findByQuery(
            @Param("email") String email,
            @Param("query") String query
    );

    @Query("SELECT d FROM Diary d JOIN FETCH d.hashTags WHERE d.user.email = :email")
    List<Diary> findAllByUserEmailWithHasTag(@Param("email") String email);
}

package com.likelion.byuldajul.board.Repository;


import com.likelion.byuldajul.board.Entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query(value = "select *" +
            " from diarys d" +
            " where :hashtag = '' or d.diary_id in (select distinct dh.diary_id" +
            " from hashtags h, diary_hashtag dh" +
            " where h.hashtag_id = dh.hashtag_id and h.name = :hashtag)", nativeQuery = true)
    List<Diary> findByHashTag(String hashtag);




}

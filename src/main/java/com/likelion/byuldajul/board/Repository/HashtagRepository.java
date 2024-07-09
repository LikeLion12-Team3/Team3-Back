package com.likelion.byuldajul.board.Repository;

import com.likelion.byuldajul.board.Entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    Optional<Hashtag> findByName(String name);

//    @Query(value = "select Hashtag.name" +
//            " from DiaryHashtag dh, Hashtag h" +
//            " where dh.id = h.id and dh.diary.Id = :Id")
//    List<String> findHashtagById(Long id);

    @Query(value = "select h.name from DiaryHashtag dh join dh.hashtag h where dh.diary.Id = :id")
    List<String> findHashtagsByDiaryId(@Param("id") Long id);


}

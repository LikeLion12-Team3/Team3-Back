package com.likelion.byuldajul.diary.Repository;

import com.likelion.byuldajul.diary.Entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    List<Hashtag> findAllByNameContainingAndDiary_User_Email(String query, String email);

    void deleteAllByDiary_Id(Long id);
}

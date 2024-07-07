package com.likelion.byuldajul.board.Repository;

import com.likelion.byuldajul.board.Entity.Diary;
import com.likelion.byuldajul.board.Entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    Optional<Hashtag> findByName(String name);

}

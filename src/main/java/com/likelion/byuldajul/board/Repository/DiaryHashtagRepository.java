package com.likelion.byuldajul.board.Repository;

import com.likelion.byuldajul.board.Entity.Diary;
import com.likelion.byuldajul.board.Entity.DiaryHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryHashtagRepository extends JpaRepository<DiaryHashtag, Long> {

}
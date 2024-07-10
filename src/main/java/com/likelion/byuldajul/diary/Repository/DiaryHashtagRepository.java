package com.likelion.byuldajul.diary.Repository;
import com.likelion.byuldajul.diary.Entity.DiaryHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryHashtagRepository extends JpaRepository<DiaryHashtag, Long> {

    void deleteAllByDiary_Id(Long diary_id);

    List<DiaryHashtag> findByDiaryId(Long diaryId);
  
}
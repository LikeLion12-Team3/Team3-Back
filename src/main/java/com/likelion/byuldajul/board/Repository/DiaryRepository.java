package com.likelion.byuldajul.board.Repository;

import com.likelion.byuldajul.board.Entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

}

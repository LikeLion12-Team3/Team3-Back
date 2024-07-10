package com.likelion.byuldajul.diary.Repository;

import com.likelion.byuldajul.diary.Entity.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {

    Idea findIdeaById(Long id);

    void deleteIdeaById(Long id);

}

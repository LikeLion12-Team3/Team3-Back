package com.likelion.byuldajul.diary.Repository;

import com.likelion.byuldajul.diary.Entity.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {

    List<Idea> findAllByUser_Email(String email);

    Idea findIdeaById(Long id);

    void deleteIdeaById(Long id);

}

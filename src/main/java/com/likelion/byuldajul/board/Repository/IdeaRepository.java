package com.likelion.byuldajul.board.Repository;

import com.likelion.byuldajul.board.Entity.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {

    Idea findIdeaById(Long id);

    void deleteIdeaById(Long id);

}

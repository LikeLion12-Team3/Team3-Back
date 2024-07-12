package com.likelion.byuldajul.diary.Repository;

import com.likelion.byuldajul.diary.Entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findAllByIdea_Id(Long id);
}


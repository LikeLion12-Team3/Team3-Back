package com.likelion.byuldajul.summary.Repository;

import com.likelion.byuldajul.summary.Entity.Summary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SummaryRepository extends JpaRepository<Summary, Long> {
    Optional<Summary> findByEmail(String email);

    Optional<Summary> findByEmailAndAndDate(String email, LocalDate localDate);
}
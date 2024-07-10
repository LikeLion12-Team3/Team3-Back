package com.likelion.byuldajul.commit.repository;

import com.likelion.byuldajul.commit.entity.Commit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CommitRepository extends JpaRepository<Commit, Long> {

    // 특정 연도와 달에 특정 유저가 올린 커밋을 조회
    @Query("SELECT c FROM Commit c WHERE YEAR(c.date) = :year AND MONTH(c.date) = :month AND c.user.email = :email")
    List<Commit> findByDateYearAndMonthAndUserEmail(@Param("year") int year, @Param("month") int month, @Param("email") String email);

    // 특정한 날에 특정 유저가 올린 커밋을 조회
    @Query("SELECT c FROM Commit c WHERE c.date = :date AND c.user.email = :email")
    List<Commit> findByDateAndUserEmail(@Param("date") LocalDate date, @Param("email") String email);
}

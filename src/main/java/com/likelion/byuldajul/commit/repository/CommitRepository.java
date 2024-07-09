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

    //ex) findByCreatedAtYearAndMonth(2024, 6)
    //      -> SELECT * FROM commits WHERE YEAR(createdAt) = 2024 AND MONTH(createdAt) = 6; 쿼리문이 날라감
    @Query("SELECT c FROM Commit c WHERE YEAR(c.createdAt) = :year AND MONTH(c.createdAt) = :month")
    List<Commit> findByCreatedAtYearAndMonth(@Param("year") int year, @Param("month") int month);

    // 특정 날짜에 해당하는 커밋들을 조회하는 메서드
    @Query("SELECT c FROM Commit c WHERE DATE(c.createdAt) = :date")
    List<Commit> findByCreatedAtDate(@Param("date") LocalDate date);
}

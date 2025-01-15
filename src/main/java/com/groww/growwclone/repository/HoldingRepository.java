package com.groww.growwclone.repository;

import com.groww.growwclone.entity.Holding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HoldingRepository extends JpaRepository<Holding, Long> {

    @Query("SELECT h FROM Holding h WHERE h.user.userId = :userId AND h.stockId = :stockId ORDER BY h.quantity ASC")
    List<Holding> findAllHoldingsForStock(
            @Param("userId") Long userId,
            @Param("stockId") Long stockId
    );

    List<Holding> findByUser_UserId(Long userId);
}

package com.tradeflow.execution.repository.jpa;

import com.tradeflow.execution.model.OrderProjection;
import com.tradeflow.event.model.Side;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderProjectionRepository  extends JpaRepository<OrderProjection, UUID> {


    @Query("SELECT o " +
            "FROM OrderProjection o " +
            "WHERE o.side != :side AND " +
            "o.price = :sharePrice AND " +
            "o.userId != :userId AND " +
            "o.quantity  = :quantity AND " +
            "o.companyId = :companyId AND " +
            "o.status = 'PENDING' ")
    Optional<OrderProjection> findOppositeOrder(@Param("side") Side side,
                                                @Param("sharePrice") Integer sharePrice,
                                                @Param("quantity") Integer quantity,
                                                @Param("companyId") UUID companyId,
                                                @Param("userId") UUID userId);
}

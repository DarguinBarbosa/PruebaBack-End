package com.darguin.prueba_back_end.infrastructure.adapter.out.persistence.repository;

import com.darguin.prueba_back_end.application.dto.response.TopStockProductResponse;
import com.darguin.prueba_back_end.infrastructure.adapter.out.persistence.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductR2dbcRepository extends ReactiveCrudRepository<ProductEntity, Long> {

    @Modifying
    @Query("UPDATE product SET stock = :stock WHERE id = :id")
    Mono<Integer> updateStockById(Long id, int stock);

    @Modifying
    @Query("UPDATE product SET name = :name WHERE id = :id")
    Mono<Integer> updateNameById(Long id, String name);

    @Query("""
            SELECT p.id AS product_id, p.name AS product_name, p.stock AS stock,
                   b.id AS branch_id, b.name AS branch_name
            FROM product p
            JOIN branch b ON p.branch_id = b.id
            WHERE b.franchise_id = :franchiseId
              AND p.stock = (SELECT MAX(p2.stock) FROM product p2 WHERE p2.branch_id = b.id)
            """)
    Flux<TopStockProductResponse> findTopStockByFranchiseId(Long franchiseId);
}

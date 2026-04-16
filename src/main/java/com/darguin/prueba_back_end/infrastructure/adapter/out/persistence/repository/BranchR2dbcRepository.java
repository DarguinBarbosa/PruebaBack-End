package com.darguin.prueba_back_end.infrastructure.adapter.out.persistence.repository;

import com.darguin.prueba_back_end.infrastructure.adapter.out.persistence.entity.BranchEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchR2dbcRepository extends ReactiveCrudRepository<BranchEntity, Long> {

    Flux<BranchEntity> findByFranchiseId(Long franchiseId);

    @Modifying
    @Query("UPDATE branch SET name = :name WHERE id = :id")
    Mono<Integer> updateNameById(Long id, String name);
}

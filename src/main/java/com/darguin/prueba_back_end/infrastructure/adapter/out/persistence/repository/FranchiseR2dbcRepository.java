package com.darguin.prueba_back_end.infrastructure.adapter.out.persistence.repository;

import com.darguin.prueba_back_end.infrastructure.adapter.out.persistence.entity.FranchiseEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface FranchiseR2dbcRepository extends ReactiveCrudRepository<FranchiseEntity, Long> {

    @Modifying
    @Query("UPDATE franchise SET name = :name WHERE id = :id")
    Mono<Integer> updateNameById(Long id, String name);
}

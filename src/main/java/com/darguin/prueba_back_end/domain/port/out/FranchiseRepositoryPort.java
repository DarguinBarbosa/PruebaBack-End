package com.darguin.prueba_back_end.domain.port.out;

import com.darguin.prueba_back_end.domain.model.Franchise;
import reactor.core.publisher.Mono;

public interface FranchiseRepositoryPort {

    Mono<Franchise> save(Franchise franchise);

    Mono<Franchise> findById(Long id);

    Mono<Franchise> updateName(Long id, String name);
}

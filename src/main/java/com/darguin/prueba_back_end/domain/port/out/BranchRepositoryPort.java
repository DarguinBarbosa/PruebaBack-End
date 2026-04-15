package com.darguin.prueba_back_end.domain.port.out;

import com.darguin.prueba_back_end.domain.model.Branch;
import reactor.core.publisher.Mono;

public interface BranchRepositoryPort {

    Mono<Branch> save(Branch branch);

    Mono<Branch> findById(Long id);

    Mono<Branch> updateName(Long id, String name);
}

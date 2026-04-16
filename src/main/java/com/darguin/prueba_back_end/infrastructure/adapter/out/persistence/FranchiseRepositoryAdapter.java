package com.darguin.prueba_back_end.infrastructure.adapter.out.persistence;

import com.darguin.prueba_back_end.domain.model.Franchise;
import com.darguin.prueba_back_end.domain.port.out.FranchiseRepositoryPort;
import com.darguin.prueba_back_end.infrastructure.adapter.out.persistence.mapper.FranchiseMapper;
import com.darguin.prueba_back_end.infrastructure.adapter.out.persistence.repository.FranchiseR2dbcRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class FranchiseRepositoryAdapter implements FranchiseRepositoryPort {

    private final FranchiseR2dbcRepository repository;

    public FranchiseRepositoryAdapter(FranchiseR2dbcRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        return repository.save(FranchiseMapper.toEntity(franchise))
                .map(FranchiseMapper::toDomain);
    }

    @Override
    public Mono<Franchise> findById(Long id) {
        return repository.findById(id)
                .map(FranchiseMapper::toDomain);
    }

    @Override
    public Mono<Franchise> updateName(Long id, String name) {
        return repository.updateNameById(id, name)
                .then(repository.findById(id))
                .map(FranchiseMapper::toDomain);
    }
}

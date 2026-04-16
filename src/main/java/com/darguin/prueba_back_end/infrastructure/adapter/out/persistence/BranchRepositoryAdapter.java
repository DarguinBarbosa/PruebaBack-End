package com.darguin.prueba_back_end.infrastructure.adapter.out.persistence;

import com.darguin.prueba_back_end.domain.model.Branch;
import com.darguin.prueba_back_end.domain.port.out.BranchRepositoryPort;
import com.darguin.prueba_back_end.infrastructure.adapter.out.persistence.mapper.BranchMapper;
import com.darguin.prueba_back_end.infrastructure.adapter.out.persistence.repository.BranchR2dbcRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class BranchRepositoryAdapter implements BranchRepositoryPort {

    private final BranchR2dbcRepository repository;

    public BranchRepositoryAdapter(BranchR2dbcRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Branch> save(Branch branch) {
        return repository.save(BranchMapper.toEntity(branch))
                .map(BranchMapper::toDomain);
    }

    @Override
    public Mono<Branch> findById(Long id) {
        return repository.findById(id)
                .map(BranchMapper::toDomain);
    }

    @Override
    public Mono<Branch> updateName(Long id, String name) {
        return repository.updateNameById(id, name)
                .then(repository.findById(id))
                .map(BranchMapper::toDomain);
    }
}

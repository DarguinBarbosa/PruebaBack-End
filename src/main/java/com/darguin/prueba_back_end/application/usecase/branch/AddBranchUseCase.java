package com.darguin.prueba_back_end.application.usecase.branch;

import com.darguin.prueba_back_end.application.dto.request.AddBranchRequest;
import com.darguin.prueba_back_end.application.dto.response.BranchResponse;
import com.darguin.prueba_back_end.application.exception.NotFoundException;
import com.darguin.prueba_back_end.domain.model.Branch;
import com.darguin.prueba_back_end.domain.port.out.BranchRepositoryPort;
import com.darguin.prueba_back_end.domain.port.out.FranchiseRepositoryPort;
import reactor.core.publisher.Mono;

public class AddBranchUseCase {

    private final FranchiseRepositoryPort franchiseRepositoryPort;
    private final BranchRepositoryPort branchRepositoryPort;

    public AddBranchUseCase(FranchiseRepositoryPort franchiseRepositoryPort,
                            BranchRepositoryPort branchRepositoryPort) {
        this.franchiseRepositoryPort = franchiseRepositoryPort;
        this.branchRepositoryPort = branchRepositoryPort;
    }

    public Mono<BranchResponse> execute(Long franchiseId, AddBranchRequest request) {
        return franchiseRepositoryPort.findById(franchiseId)
                .switchIfEmpty(Mono.error(new NotFoundException("Franquicia no encontrada: " + franchiseId)))
                .flatMap(f -> {
                    Branch branch = new Branch(null, request.getName(), franchiseId);
                    return branchRepositoryPort.save(branch);
                })
                .map(b -> new BranchResponse(b.getId(), b.getName(), b.getFranchiseId()));
    }
}

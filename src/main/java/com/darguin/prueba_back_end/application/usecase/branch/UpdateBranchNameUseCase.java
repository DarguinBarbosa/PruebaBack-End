package com.darguin.prueba_back_end.application.usecase.branch;

import com.darguin.prueba_back_end.application.dto.request.UpdateNameRequest;
import com.darguin.prueba_back_end.application.dto.response.BranchResponse;
import com.darguin.prueba_back_end.application.exception.NotFoundException;
import com.darguin.prueba_back_end.domain.port.out.BranchRepositoryPort;
import reactor.core.publisher.Mono;

public class UpdateBranchNameUseCase {

    private final BranchRepositoryPort branchRepositoryPort;

    public UpdateBranchNameUseCase(BranchRepositoryPort branchRepositoryPort) {
        this.branchRepositoryPort = branchRepositoryPort;
    }

    public Mono<BranchResponse> execute(Long id, UpdateNameRequest request) {
        return branchRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Sucursal no encontrada: " + id)))
                .flatMap(b -> branchRepositoryPort.updateName(id, request.getName()))
                .map(b -> new BranchResponse(b.getId(), b.getName(), b.getFranchiseId()));
    }
}

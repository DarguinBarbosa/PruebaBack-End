package com.darguin.prueba_back_end.application.usecase.franchise;

import com.darguin.prueba_back_end.application.dto.request.UpdateNameRequest;
import com.darguin.prueba_back_end.application.dto.response.FranchiseResponse;
import com.darguin.prueba_back_end.application.exception.NotFoundException;
import com.darguin.prueba_back_end.domain.port.out.FranchiseRepositoryPort;
import reactor.core.publisher.Mono;

public class UpdateFranchiseNameUseCase {

    private final FranchiseRepositoryPort franchiseRepositoryPort;

    public UpdateFranchiseNameUseCase(FranchiseRepositoryPort franchiseRepositoryPort) {
        this.franchiseRepositoryPort = franchiseRepositoryPort;
    }

    public Mono<FranchiseResponse> execute(Long id, UpdateNameRequest request) {
        return franchiseRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Franquicia no encontrada: " + id)))
                .flatMap(f -> franchiseRepositoryPort.updateName(id, request.getName()))
                .map(f -> new FranchiseResponse(f.getId(), f.getName()));
    }
}

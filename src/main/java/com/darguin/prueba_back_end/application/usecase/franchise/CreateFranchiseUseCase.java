package com.darguin.prueba_back_end.application.usecase.franchise;

import com.darguin.prueba_back_end.application.dto.request.CreateFranchiseRequest;
import com.darguin.prueba_back_end.application.dto.response.FranchiseResponse;
import com.darguin.prueba_back_end.domain.model.Franchise;
import com.darguin.prueba_back_end.domain.port.out.FranchiseRepositoryPort;
import reactor.core.publisher.Mono;

public class CreateFranchiseUseCase {

    private final FranchiseRepositoryPort franchiseRepositoryPort;

    public CreateFranchiseUseCase(FranchiseRepositoryPort franchiseRepositoryPort) {
        this.franchiseRepositoryPort = franchiseRepositoryPort;
    }

    public Mono<FranchiseResponse> execute(CreateFranchiseRequest request) {
        Franchise franchise = new Franchise(null, request.getName());
        return franchiseRepositoryPort.save(franchise)
                .map(f -> new FranchiseResponse(f.getId(), f.getName()));
    }
}

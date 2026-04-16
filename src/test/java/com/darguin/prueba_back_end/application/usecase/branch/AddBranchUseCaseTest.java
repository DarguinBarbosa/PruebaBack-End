package com.darguin.prueba_back_end.application.usecase.branch;

import com.darguin.prueba_back_end.application.dto.request.AddBranchRequest;
import com.darguin.prueba_back_end.application.exception.NotFoundException;
import com.darguin.prueba_back_end.domain.model.Branch;
import com.darguin.prueba_back_end.domain.model.Franchise;
import com.darguin.prueba_back_end.domain.port.out.BranchRepositoryPort;
import com.darguin.prueba_back_end.domain.port.out.FranchiseRepositoryPort;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class AddBranchUseCaseTest {

    private final FranchiseRepositoryPort franchiseRepo = mock(FranchiseRepositoryPort.class);
    private final BranchRepositoryPort branchRepo = mock(BranchRepositoryPort.class);
    private final AddBranchUseCase useCase = new AddBranchUseCase(franchiseRepo, branchRepo);

    @Test
    void agrega_sucursal_cuando_franquicia_existe() {
        when(franchiseRepo.findById(1L)).thenReturn(Mono.just(new Franchise(1L, "F")));
        when(branchRepo.save(any(Branch.class)))
                .thenReturn(Mono.just(new Branch(10L, "Centro", 1L)));

        StepVerifier.create(useCase.execute(1L, new AddBranchRequest("Centro")))
                .expectNextMatches(r -> r.getId().equals(10L)
                        && r.getName().equals("Centro")
                        && r.getFranchiseId().equals(1L))
                .verifyComplete();
    }

    @Test
    void falla_cuando_franquicia_no_existe() {
        when(franchiseRepo.findById(99L)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.execute(99L, new AddBranchRequest("X")))
                .expectError(NotFoundException.class)
                .verify();

        verifyNoInteractions(branchRepo);
    }
}

package com.darguin.prueba_back_end.application.usecase.branch;

import com.darguin.prueba_back_end.application.dto.request.UpdateNameRequest;
import com.darguin.prueba_back_end.application.exception.NotFoundException;
import com.darguin.prueba_back_end.domain.model.Branch;
import com.darguin.prueba_back_end.domain.port.out.BranchRepositoryPort;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UpdateBranchNameUseCaseTest {

    private final BranchRepositoryPort repository = mock(BranchRepositoryPort.class);
    private final UpdateBranchNameUseCase useCase = new UpdateBranchNameUseCase(repository);

    @Test
    void actualiza_nombre_cuando_sucursal_existe() {
        when(repository.findById(1L)).thenReturn(Mono.just(new Branch(1L, "Vieja", 10L)));
        when(repository.updateName(1L, "Nueva")).thenReturn(Mono.just(new Branch(1L, "Nueva", 10L)));

        StepVerifier.create(useCase.execute(1L, new UpdateNameRequest("Nueva")))
                .expectNextMatches(r -> r.getName().equals("Nueva") && r.getFranchiseId().equals(10L))
                .verifyComplete();
    }

    @Test
    void falla_cuando_sucursal_no_existe() {
        when(repository.findById(99L)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.execute(99L, new UpdateNameRequest("X")))
                .expectError(NotFoundException.class)
                .verify();
    }
}

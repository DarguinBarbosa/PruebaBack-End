package com.darguin.prueba_back_end.application.usecase.franchise;

import com.darguin.prueba_back_end.application.dto.request.UpdateNameRequest;
import com.darguin.prueba_back_end.application.exception.NotFoundException;
import com.darguin.prueba_back_end.domain.model.Franchise;
import com.darguin.prueba_back_end.domain.port.out.FranchiseRepositoryPort;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class UpdateFranchiseNameUseCaseTest {

    private final FranchiseRepositoryPort repository = mock(FranchiseRepositoryPort.class);
    private final UpdateFranchiseNameUseCase useCase = new UpdateFranchiseNameUseCase(repository);

    @Test
    void actualiza_nombre_cuando_franquicia_existe() {
        when(repository.findById(1L)).thenReturn(Mono.just(new Franchise(1L, "Viejo")));
        when(repository.updateName(1L, "Nuevo")).thenReturn(Mono.just(new Franchise(1L, "Nuevo")));

        StepVerifier.create(useCase.execute(1L, new UpdateNameRequest("Nuevo")))
                .expectNextMatches(r -> r.getId().equals(1L) && r.getName().equals("Nuevo"))
                .verifyComplete();
    }

    @Test
    void lanza_NotFoundException_cuando_franquicia_no_existe() {
        when(repository.findById(99L)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.execute(99L, new UpdateNameRequest("Nuevo")))
                .expectErrorMatches(e -> e instanceof NotFoundException
                        && e.getMessage().contains("99"))
                .verify();

        verify(repository).findById(99L);
        verifyNoMoreInteractions(repository);
    }
}

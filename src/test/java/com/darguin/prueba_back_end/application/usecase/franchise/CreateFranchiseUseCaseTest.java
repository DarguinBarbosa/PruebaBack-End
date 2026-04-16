package com.darguin.prueba_back_end.application.usecase.franchise;

import com.darguin.prueba_back_end.application.dto.request.CreateFranchiseRequest;
import com.darguin.prueba_back_end.domain.model.Franchise;
import com.darguin.prueba_back_end.domain.port.out.FranchiseRepositoryPort;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateFranchiseUseCaseTest {

    private final FranchiseRepositoryPort repository = mock(FranchiseRepositoryPort.class);
    private final CreateFranchiseUseCase useCase = new CreateFranchiseUseCase(repository);

    @Test
    void persiste_franquicia_y_devuelve_response() {
        when(repository.save(any(Franchise.class)))
                .thenReturn(Mono.just(new Franchise(1L, "Franquicia Norte")));

        StepVerifier.create(useCase.execute(new CreateFranchiseRequest("Franquicia Norte")))
                .assertNext(res -> {
                    assertThat(res.getId()).isEqualTo(1L);
                    assertThat(res.getName()).isEqualTo("Franquicia Norte");
                })
                .verifyComplete();

        ArgumentCaptor<Franchise> captor = ArgumentCaptor.forClass(Franchise.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getId()).isNull();
        assertThat(captor.getValue().getName()).isEqualTo("Franquicia Norte");
    }
}

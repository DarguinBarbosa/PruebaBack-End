package com.darguin.prueba_back_end.application.usecase.product;

import com.darguin.prueba_back_end.application.dto.request.UpdateNameRequest;
import com.darguin.prueba_back_end.application.exception.NotFoundException;
import com.darguin.prueba_back_end.domain.model.Product;
import com.darguin.prueba_back_end.domain.port.out.ProductRepositoryPort;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UpdateProductNameUseCaseTest {

    private final ProductRepositoryPort repository = mock(ProductRepositoryPort.class);
    private final UpdateProductNameUseCase useCase = new UpdateProductNameUseCase(repository);

    @Test
    void actualiza_nombre_cuando_producto_existe() {
        when(repository.findById(1L)).thenReturn(Mono.just(new Product(1L, "Viejo", 10, 5L)));
        when(repository.updateName(1L, "Nuevo")).thenReturn(Mono.just(new Product(1L, "Nuevo", 10, 5L)));

        StepVerifier.create(useCase.execute(1L, new UpdateNameRequest("Nuevo")))
                .expectNextMatches(r -> r.getName().equals("Nuevo"))
                .verifyComplete();
    }

    @Test
    void falla_cuando_producto_no_existe() {
        when(repository.findById(99L)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.execute(99L, new UpdateNameRequest("X")))
                .expectError(NotFoundException.class)
                .verify();
    }
}

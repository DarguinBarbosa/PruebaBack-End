package com.darguin.prueba_back_end.application.usecase.product;

import com.darguin.prueba_back_end.application.exception.NotFoundException;
import com.darguin.prueba_back_end.domain.model.Product;
import com.darguin.prueba_back_end.domain.port.out.ProductRepositoryPort;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RemoveProductUseCaseTest {

    private final ProductRepositoryPort repository = mock(ProductRepositoryPort.class);
    private final RemoveProductUseCase useCase = new RemoveProductUseCase(repository);

    @Test
    void elimina_cuando_producto_pertenece_a_la_sucursal() {
        when(repository.findById(7L)).thenReturn(Mono.just(new Product(7L, "P", 10, 3L)));
        when(repository.deleteById(7L)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.execute(3L, 7L))
                .verifyComplete();

        verify(repository).deleteById(7L);
    }

    @Test
    void falla_cuando_producto_no_existe() {
        when(repository.findById(7L)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.execute(3L, 7L))
                .expectError(NotFoundException.class)
                .verify();
    }

    @Test
    void falla_cuando_producto_no_pertenece_a_la_sucursal() {
        when(repository.findById(7L)).thenReturn(Mono.just(new Product(7L, "P", 10, 99L)));

        StepVerifier.create(useCase.execute(3L, 7L))
                .expectErrorMatches(e -> e instanceof NotFoundException
                        && e.getMessage().contains("no pertenece"))
                .verify();
    }
}

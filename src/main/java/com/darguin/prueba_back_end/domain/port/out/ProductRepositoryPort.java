package com.darguin.prueba_back_end.domain.port.out;

import com.darguin.prueba_back_end.application.dto.response.TopStockProductResponse;
import com.darguin.prueba_back_end.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepositoryPort {

    Mono<Product> save(Product product);

    Mono<Void> deleteById(Long id);

    Mono<Product> findById(Long id);

    Mono<Product> updateStock(Long id, int stock);

    Mono<Product> updateName(Long id, String name);

    Flux<TopStockProductResponse> findTopStockProductsByFranchiseId(Long franchiseId);
}

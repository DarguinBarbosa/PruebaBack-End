package com.darguin.prueba_back_end.infrastructure.adapter.out.persistence;

import com.darguin.prueba_back_end.application.dto.response.TopStockProductResponse;
import com.darguin.prueba_back_end.domain.model.Product;
import com.darguin.prueba_back_end.domain.port.out.ProductRepositoryPort;
import com.darguin.prueba_back_end.infrastructure.adapter.out.persistence.mapper.ProductMapper;
import com.darguin.prueba_back_end.infrastructure.adapter.out.persistence.repository.ProductR2dbcRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private final ProductR2dbcRepository repository;

    public ProductRepositoryAdapter(ProductR2dbcRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Product> save(Product product) {
        return repository.save(ProductMapper.toEntity(product))
                .map(ProductMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<Product> findById(Long id) {
        return repository.findById(id)
                .map(ProductMapper::toDomain);
    }

    @Override
    public Mono<Product> updateStock(Long id, int stock) {
        return repository.updateStockById(id, stock)
                .then(repository.findById(id))
                .map(ProductMapper::toDomain);
    }

    @Override
    public Mono<Product> updateName(Long id, String name) {
        return repository.updateNameById(id, name)
                .then(repository.findById(id))
                .map(ProductMapper::toDomain);
    }

    @Override
    public Flux<TopStockProductResponse> findTopStockProductsByFranchiseId(Long franchiseId) {
        return repository.findTopStockByFranchiseId(franchiseId);
    }
}

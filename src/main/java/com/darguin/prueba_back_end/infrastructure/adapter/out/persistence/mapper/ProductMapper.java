package com.darguin.prueba_back_end.infrastructure.adapter.out.persistence.mapper;

import com.darguin.prueba_back_end.domain.model.Product;
import com.darguin.prueba_back_end.infrastructure.adapter.out.persistence.entity.ProductEntity;

public final class ProductMapper {

    private ProductMapper() {}

    public static Product toDomain(ProductEntity entity) {
        return new Product(entity.getId(), entity.getName(), entity.getStock(), entity.getBranchId());
    }

    public static ProductEntity toEntity(Product domain) {
        return new ProductEntity(domain.getId(), domain.getName(), domain.getStock(), domain.getBranchId());
    }
}

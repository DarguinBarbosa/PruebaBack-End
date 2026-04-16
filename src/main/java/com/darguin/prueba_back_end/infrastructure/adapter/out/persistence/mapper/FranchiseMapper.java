package com.darguin.prueba_back_end.infrastructure.adapter.out.persistence.mapper;

import com.darguin.prueba_back_end.domain.model.Franchise;
import com.darguin.prueba_back_end.infrastructure.adapter.out.persistence.entity.FranchiseEntity;

public final class FranchiseMapper {

    private FranchiseMapper() {}

    public static Franchise toDomain(FranchiseEntity entity) {
        return new Franchise(entity.getId(), entity.getName());
    }

    public static FranchiseEntity toEntity(Franchise domain) {
        return new FranchiseEntity(domain.getId(), domain.getName());
    }
}

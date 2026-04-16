package com.darguin.prueba_back_end.infrastructure.adapter.out.persistence.mapper;

import com.darguin.prueba_back_end.domain.model.Branch;
import com.darguin.prueba_back_end.infrastructure.adapter.out.persistence.entity.BranchEntity;

public final class BranchMapper {

    private BranchMapper() {}

    public static Branch toDomain(BranchEntity entity) {
        return new Branch(entity.getId(), entity.getName(), entity.getFranchiseId());
    }

    public static BranchEntity toEntity(Branch domain) {
        return new BranchEntity(domain.getId(), domain.getName(), domain.getFranchiseId());
    }
}

package com.hieuph.todosmanagement.specification.Impl;

import com.hieuph.todosmanagement.specification.SpecificationRelation;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;


@Component
public class SpecificationRelationImpl<T, R> implements SpecificationRelation {
    @Override
    public Specification joinEqual(String keyjoin, String key, Object value) {
        return (root, query, criteriaBuilder) -> {
            Join<R, T> join = root.join(keyjoin);
            return criteriaBuilder.equal(join.get(key), value);
        };
    }
}

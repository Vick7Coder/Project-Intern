package com.hieuph.todosmanagement.specification.Impl;

import com.hieuph.todosmanagement.specification.GenericSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class GenericSpecificationImpl<T> implements GenericSpecification<T> {
    @Override
    public Specification<T> like(String key, String value) {
        if (!StringUtils.hasText(value)){
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.lower(root.get(key)), value.toLowerCase())

        );
    }

    @Override
    public Specification<T> equals(String key, Object value) {
        if (value == null) {
            return null;
        }
        return (
                ((root, query, criteriaBuilder) -> criteriaBuilder.equal(
                        root.get(key),
                        value
                ))
        );
    }

    @Override
    public Specification equalIgnoreCase(String key, String value) {
        if(!StringUtils.hasText(value)){
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(
                criteriaBuilder.lower(root.get(key)),
                value.toLowerCase()
        ));
    }


}

package com.hieuph.todosmanagement.Filter.User;


import com.hieuph.todosmanagement.Filter.Category.CategoryFilter;
import com.hieuph.todosmanagement.entity.Category;
import com.hieuph.todosmanagement.entity.User;
import com.hieuph.todosmanagement.specification.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GenericUserSpecification {
    @Autowired
    private GenericSpecification<User> genericSpecification;

    public Specification<User> generic(UserFilter userFilter){
        Specification<User> specification = null;
        List<Specification<User>> specifications = new ArrayList<>();
        if(userFilter.getUsername() != null){
            specifications.add(genericSpecification.like(UserFilter.FIELD_NAME, "%"+ userFilter.getUsername()+"%"));
        }
        if(userFilter.getEmail() != null){
            specifications.add(genericSpecification.like(UserFilter.FIELD_email, "%"+userFilter.getEmail()+"%"));
        }

        if (userFilter.getEnabled() != null){
            if (userFilter.getEnabled().equals(true)) {
                specifications.add(genericSpecification.equals(UserFilter.FIELD_ENABLE, true));
            } else if (userFilter.getEnabled().equals(false)) {
                specifications.add(genericSpecification.equals(UserFilter.FIELD_ENABLE, false));
            }
        }

        List<Specification<User>> result = new ArrayList<>();

        for (int i = 0; i < specifications.size(); i++) {
            if (specifications.get(i) != null) {
                result.add(specifications.get(i));
            }
        }

        for (int i = 0; i < result.size(); i++) {
            if (specification == null) {
                specification = Specification.where(result.get(i));
            } else {
                specification = specification.and(result.get(i));
            }
        }

        return specification;
    }

}

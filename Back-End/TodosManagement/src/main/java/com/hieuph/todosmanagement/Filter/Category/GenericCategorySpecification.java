package com.hieuph.todosmanagement.Filter.Category;

import com.hieuph.todosmanagement.Filter.User.UserFilter;
import com.hieuph.todosmanagement.entity.Category;
import com.hieuph.todosmanagement.entity.User;
import com.hieuph.todosmanagement.specification.GenericSpecification;
import com.hieuph.todosmanagement.specification.SpecificationRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GenericCategorySpecification {
    @Autowired
    private GenericSpecification<Category> genericSpecification;
    @Autowired
    private SpecificationRelation<Category, User> catJoinUser;

    public Specification<Category> generic(CategoryFilter categoryFilter){
        Specification<Category> specification = null;
        List<Specification<Category>> specifications = new ArrayList<>();
        if(categoryFilter.getName() != null){
            specifications.add(genericSpecification.like(CategoryFilter.FIELD_NAME, "%"+ categoryFilter.getName()+"%"));
        }
        if(categoryFilter.getUser() !=null){
            specifications.add(catJoinUser.joinEqual(CategoryFilter.FIELD_USER,"id",categoryFilter.getUser()));
        }
        if (categoryFilter.getEnabled() != null){
            if (categoryFilter.getEnabled().equals(true)) {
                specifications.add(genericSpecification.equals(CategoryFilter.FIELD_ENABLE, true));
            } else if (categoryFilter.getEnabled().equals(false)) {
                specifications.add(genericSpecification.equals(CategoryFilter.FIELD_ENABLE, false));
            }
        }

        List<Specification<Category>> result = new ArrayList<>();

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

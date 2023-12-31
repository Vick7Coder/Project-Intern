package com.hieuph.todosmanagement.Filter.Todo;

import com.hieuph.todosmanagement.Filter.User.UserFilter;
import com.hieuph.todosmanagement.entity.Category;
import com.hieuph.todosmanagement.entity.Todo;
import com.hieuph.todosmanagement.entity.User;
import com.hieuph.todosmanagement.specification.GenericSpecification;
import com.hieuph.todosmanagement.specification.SpecificationRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class GenericTodoSpecification {
    @Autowired
    private GenericSpecification<Todo> genericSpecification;
    @Autowired
    private SpecificationRelation<Todo, User> todojoinEqualUser;
    @Autowired
    private SpecificationRelation<Todo, Category> todojoinEqualCategory;


    public Specification<Todo> generic(TodoFilter todoFilter) {
        Specification<Todo> specification = null;
        List<Specification<Todo>> specifications = new ArrayList<>();
        if (todoFilter.getDescription() != null) {
            specifications.add(genericSpecification.like(TodoFilter.FIELD_DESCRIPTION, "%" + todoFilter.getDescription() + "%"));
        }
        if (todoFilter.getUser() != null) {
            specifications.add(todojoinEqualUser.joinEqual(TodoFilter.FIELD_USER, "id", todoFilter.getUser()));
        }
        if (todoFilter.getCategory() != null) {
            specifications.add(todojoinEqualCategory.joinEqual(TodoFilter.FIELD_CATEGORY, "id", todoFilter.getCategory()));
        }
        if (todoFilter.getDone() != null){
            if (todoFilter.getDone().equals(true)) {
                specifications.add(genericSpecification.equals(TodoFilter.FIELD_DONE, true));
            } else if (todoFilter.getDone().equals(false)) {
                specifications.add(genericSpecification.equals(TodoFilter.FIELD_DONE, false));
            }
        }

        List<Specification<Todo>> result = new ArrayList<>();

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



package com.hieuph.todosmanagement.service;

import com.hieuph.todosmanagement.Filter.Category.CategoryFilter;
import com.hieuph.todosmanagement.dto.request.CategoryDto;
import com.hieuph.todosmanagement.dto.request.Paging.PagingRequest;
import com.hieuph.todosmanagement.entity.Category;
import com.hieuph.todosmanagement.entity.User;

import java.util.List;

public interface CategoryService {
    List<Category> getAll(Integer userId);

//    List<Category> getAll(Integer userId, CategoryFilter categoryFilter);
    List<Category> getAll(CategoryFilter categoryFilter, PagingRequest pagingRequest);

    List<Category> getAllEnabled(Integer userId);


    Category get(int id);

    Category create(CategoryDto categoryDto, User user);
    void update(int id, CategoryDto categoryDto);
    void enable(int id);
    void delete(int id);

    Category findById(int id);


    Category findByName(String name);

    Long count(CategoryFilter categoryFilter);


}

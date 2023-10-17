package com.hieuph.todosmanagement.service;

import com.hieuph.todosmanagement.dto.request.CategoryDto;
import com.hieuph.todosmanagement.entity.Category;
import com.hieuph.todosmanagement.entity.User;

import java.util.List;

public interface CategoryService {
    List<Category> getAll(User user);
    List<Category> getAllEnabled(Integer userId);

    Category get(int id);

    Category create(CategoryDto categoryDto, User user);
    void update(int id, CategoryDto categoryDto);
    void enable(int id);
    void delete(int id);

    Category findById(int id);


}

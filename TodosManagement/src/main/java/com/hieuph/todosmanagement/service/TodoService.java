package com.hieuph.todosmanagement.service;

import com.hieuph.todosmanagement.dto.request.CategoryDto;
import com.hieuph.todosmanagement.dto.request.TodoDto;
import com.hieuph.todosmanagement.entity.Category;
import com.hieuph.todosmanagement.entity.Todo;
import com.hieuph.todosmanagement.entity.User;

import java.util.List;

public interface TodoService {
    List<Todo> getAll(User user);

    List<Todo> getAllByCategory(User user, Integer catId);

    List<Todo> getAllUnfinished(Integer userId);

    List<Todo> getALlFinished(Integer userId);

    Todo create(TodoDto todoDto, User user);
    void update(int id, TodoDto todoDto);

    Todo findById(int id);
    void delete(int id);

    void switchState(int id);
}

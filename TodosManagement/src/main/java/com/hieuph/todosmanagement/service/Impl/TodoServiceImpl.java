package com.hieuph.todosmanagement.service.Impl;

import com.hieuph.todosmanagement.dto.request.TodoDto;
import com.hieuph.todosmanagement.entity.Category;
import com.hieuph.todosmanagement.entity.Note;
import com.hieuph.todosmanagement.entity.Todo;
import com.hieuph.todosmanagement.entity.User;
import com.hieuph.todosmanagement.exception.NotFoundException;
import com.hieuph.todosmanagement.repository.CategoryRepository;
import com.hieuph.todosmanagement.repository.NoteRepository;
import com.hieuph.todosmanagement.repository.TodoRepository;
import com.hieuph.todosmanagement.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

@Service
public class TodoServiceImpl implements TodoService {
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private NoteRepository noteRepository;
    @Override
    public List<Todo> getAll(User user) {
        return todoRepository.findByUser(user);
    }

    @Override
    public List<Todo> getAllByCategory(User user, Integer catId) {
        List<Todo> todoList = this.getAll(user);
        Predicate<? super Todo> predicate=
                td -> td.getCategory().getId() == catId;
        return todoList.stream().filter(predicate).toList();
    }

    @Override
    public List<Todo> getAllUnfinished(Integer userId) {
        return todoRepository.findAllUnfinished(userId);
    }

    @Override
    public List<Todo> getALlFinished(Integer userId) {
        return todoRepository.findAllFinished(userId);
    }

    @Override
    public Todo create(TodoDto todoDto, User user) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

        Date targetDate;
        Integer cate = Integer.parseInt(todoDto.getCatId());
        System.out.println(cate instanceof Integer);
        try{
            targetDate = sdf.parse(todoDto.getTargetDate());
            Date date = Calendar.getInstance().getTime();
            Todo newTodo = new Todo();
            newTodo.setDescription(todoDto.getDescription());
            newTodo.setCreateDate(date);
            newTodo.setTargetDate(targetDate);
            newTodo.setDone(false);
            Category category = categoryRepository.findById(cate).orElseThrow(() -> new NotFoundException("Not Found Category!"));
            newTodo.setCategory(category);
            newTodo.setUser(user);
            todoRepository.save(newTodo);
            return newTodo;
        }
        catch (Exception ex){
            throw new RuntimeException("Invalid date format", ex);
        }
    }

    @Override
    public void update(int id, TodoDto todoDto) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

        Date targetDate;
        Integer cate = Integer.parseInt(todoDto.getCatId());
        try{
            targetDate = sdf.parse(todoDto.getTargetDate());
            Todo todo = this.findById(id);
            todo.setDescription(todoDto.getDescription());
            todo.setTargetDate(targetDate);
            Category category = categoryRepository.findById(cate)
                    .orElseThrow(() -> new NotFoundException("Not Found Category!"));
            todo.setCategory(category);
            todoRepository.save(todo);
        }
        catch (Exception e){
            throw new RuntimeException("Invalid date format", e);
        }
    }

    @Override
    public Todo findById(int id) {
        return todoRepository.findById(id).orElseThrow(()->new NotFoundException("Not Found Todo!"));
    }

    @Override
    public void delete(int id) {
        List<Note> notes = noteRepository.findByTodoId(id);
        for(Note note:notes){
            noteRepository.deleteById(note.getId());
        }
        todoRepository.deleteById(id);

    }

    @Override
    public void switchState(int id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found Todo with ID: "+id));
        todo.setDone(!todo.isDone());
        todoRepository.save(todo);
    }
}

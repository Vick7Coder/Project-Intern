package com.hieuph.todosmanagement.service;

import com.hieuph.todosmanagement.dto.request.NoteDto;
import com.hieuph.todosmanagement.entity.Note;
import com.hieuph.todosmanagement.entity.Todo;
import com.hieuph.todosmanagement.entity.User;

import java.util.List;

public interface NoteService {
    Note create(NoteDto noteDto);
    void delete(int id);
    List<Note> getAllByTodo(Integer toDo);
}

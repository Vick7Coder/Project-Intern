package com.hieuph.todosmanagement.service.Impl;

import com.hieuph.todosmanagement.dto.request.NoteDto;
import com.hieuph.todosmanagement.entity.Note;
import com.hieuph.todosmanagement.entity.Todo;
import com.hieuph.todosmanagement.exception.NotFoundException;
import com.hieuph.todosmanagement.repository.NoteRepository;
import com.hieuph.todosmanagement.repository.TodoRepository;
import com.hieuph.todosmanagement.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private TodoRepository todoRepository;
    @Override
    public Note create(NoteDto noteDto) {
        Date date = Calendar.getInstance().getTime();
        Note newNote = new Note();
        newNote.setDescription(noteDto.getDescription());
        newNote.setDateTime(date);
        Todo todo = todoRepository.findById(noteDto.getTodoId()).orElseThrow(()->new NotFoundException("Not Found Todo!"));
        newNote.setTodo(todo);
        noteRepository.save(newNote);
        return newNote;
    }

    @Override
    public void delete(int id) {
        noteRepository.deleteById(id);
    }

    @Override
    public List<Note> getAllByTodo(Integer toDo) {
        return noteRepository.findByTodoId(toDo);
    }
}

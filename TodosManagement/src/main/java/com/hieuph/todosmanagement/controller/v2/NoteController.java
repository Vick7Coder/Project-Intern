package com.hieuph.todosmanagement.controller.v2;

import com.hieuph.todosmanagement.dto.request.NoteDto;
import com.hieuph.todosmanagement.dto.response.MessageResponse;
import com.hieuph.todosmanagement.entity.Note;
import com.hieuph.todosmanagement.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v2/note")
@RestController
@Slf4j
public class NoteController {
    @Autowired
    private NoteService noteService;

    @GetMapping("/todo/{id}")
    public ResponseEntity<?> getAllByTodo(@PathVariable int id){
        List<Note> notes = noteService.getAllByTodo(id);
        return ResponseEntity.ok(notes);
    }
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody NoteDto noteDto){
        noteService.create(noteDto);
        return ResponseEntity.ok(new MessageResponse("Create Note Successfully!"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        noteService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Delete Note Successfully!"));
    }
}

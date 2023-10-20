package com.hieuph.todosmanagement.repository;

import com.hieuph.todosmanagement.entity.Note;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
    @Query("select n from Note n where n.todo.id = ?1")
    List<Note> findByTodoId(Integer todoId);

}

package com.hieuph.todosmanagement.repository;

import com.hieuph.todosmanagement.entity.Category;
import com.hieuph.todosmanagement.entity.Todo;
import com.hieuph.todosmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer>, JpaSpecificationExecutor<Todo> {
    @Query("select t from Todo t where t.user = ?1")
    List<Todo> findByUser(User user);

    @Query("select t from Todo t where t.user.id = ?1 and t.isDone = false ")
    List<Todo> findAllUnfinished(Integer userId);
    @Query("select t from Todo t where t.user.id = ?1 and t.isDone = true")
    List<Todo> findAllFinished(Integer userId);

}
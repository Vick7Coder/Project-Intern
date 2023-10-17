package com.hieuph.todosmanagement.repository;

import com.hieuph.todosmanagement.entity.Category;
import com.hieuph.todosmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByUser(User user);
    @Query("select c from Category c where c.user.id = ?1 and c.enabled = true ")
    List<Category> findAllEnableByUser(Integer userId);
    @Query("select c from Category c where c.user.id = ?1 and c.name = ?2 ")
    Optional<Category> findByName(Integer userId, String name);

    Optional<Category> findById(int id);


}

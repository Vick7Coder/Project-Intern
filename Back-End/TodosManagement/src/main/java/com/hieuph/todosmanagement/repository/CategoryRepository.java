package com.hieuph.todosmanagement.repository;

import com.hieuph.todosmanagement.Filter.Category.CategoryFilter;
import com.hieuph.todosmanagement.entity.Category;
import com.hieuph.todosmanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category> {
    @Query("select c from Category c where c.user.id = ?#{#userId}")
    List<Category> findByUser(Integer userId);

    @Query("select c from Category c where c.user.id = ?1 and c.enabled = true ")
    List<Category> findAllEnableByUser(Integer userId);
    @Query("select c from Category c where c.user.id = ?1 and c.name = ?2 ")
    Optional<Category> findByName(Integer userId, String name);

    @Query("select c from Category c where c.name = ?1")
    Category findByName(String name);

    Optional<Category> findById(int id);




}

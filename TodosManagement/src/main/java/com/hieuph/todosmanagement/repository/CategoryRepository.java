package com.hieuph.todosmanagement.repository;

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
    List<Category> findByUser(User user);
    @Query("select c from Category c where c.user = ?1 and c.name LIKE %?2%")
    List<Category> findByUser(User user, String name);
    Page<Category> findByUser(User user, Pageable pageable, Specification<Category> spec);
    @Query("select c from Category c where c.user.id = ?1 and c.enabled = true ")
    List<Category> findAllEnableByUser(Integer userId);
    @Query("select c from Category c where c.user.id = ?1 and c.name = ?2 ")
    Optional<Category> findByName(Integer userId, String name);

    Optional<Category> findById(int id);


}

package com.hieuph.todosmanagement.repository;

import com.hieuph.todosmanagement.entity.ERole;
import com.hieuph.todosmanagement.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole role);
}

package com.hieuph.todosmanagement.dto.response;

import com.hieuph.todosmanagement.entity.Role;
import com.hieuph.todosmanagement.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserI4Response {
    private int id;
    private String username;
    private String email;
    private boolean enabled;
    private Set<Role> roles = new HashSet<>();
}

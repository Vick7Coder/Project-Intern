package com.hieuph.todosmanagement.dto.response;

import com.hieuph.todosmanagement.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserI4Response {
    private User user;
}

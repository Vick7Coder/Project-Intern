package com.hieuph.todosmanagement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LoginDto {
    private String username;
    private String password;
}

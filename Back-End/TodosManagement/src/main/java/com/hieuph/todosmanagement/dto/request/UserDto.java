package com.hieuph.todosmanagement.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotNull(message = "Thiếu Username")
    @NotEmpty(message = "Thiếu Username")
    @Size(min = 5, max = 15, message = "Username chứa từ 5 đến 15 ký tự")
    private String username;
    @NotNull(message = "Thiếu Password")
    @NotEmpty(message = "Thiếu Password")
    @Size(min = 5, max = 15, message = "Password chứa từ 5 đến 15 ký tự")
    private String password;
    @NotNull(message = "Thiếu Username")
    @NotEmpty(message = "Thiếu Username")
    @Email(message = "Sai!")
    private String email;

}

package com.hieuph.todosmanagement.dto.request;

import com.hieuph.todosmanagement.entity.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTodoDto {
    private int id = 50914;
    @NotNull(message = "Not null!")
    @NotEmpty(message = "Empty description!")
    private String description;
    @NotNull(message = "Not null!")
    private String targetDate;
    @NotNull(message = "Not null!")
    private Category category;
    private String username;
}

package com.hieuph.todosmanagement.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryDto {
    @NotEmpty(message = "Empty name!")
    @NotNull(message = "Null name!")
    private String name;
    private boolean enabled = true;
    private String username;
}

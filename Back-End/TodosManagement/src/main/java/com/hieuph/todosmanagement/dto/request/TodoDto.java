package com.hieuph.todosmanagement.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class TodoDto {
    private int id = 50914;
    @NotNull(message = "Not null!")
    @NotEmpty(message = "Empty description!")
    private String description;
    @NotNull(message = "Not null!")
    private String targetDate;
    @NotNull(message = "Not null!")
    private String catId;
    private String username;
}

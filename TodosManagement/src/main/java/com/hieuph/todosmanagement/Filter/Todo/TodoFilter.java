package com.hieuph.todosmanagement.Filter.Todo;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TodoFilter {
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_CATEGORY = "category.id";
    public static final String FIELD_USER = "user.id";

    private String description;
    private int category;
    private int user;

}

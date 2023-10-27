package com.hieuph.todosmanagement.Filter.Todo;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TodoFilter {
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_CATEGORY = "category";
    public static final String FIELD_USER = "user";
    public static final String FIELD_DONE = "isDone";

    private String description;
    private Long category;
    private Long user;
    private Boolean done;

}

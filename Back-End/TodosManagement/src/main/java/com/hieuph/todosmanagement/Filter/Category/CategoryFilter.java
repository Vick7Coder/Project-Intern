package com.hieuph.todosmanagement.Filter.Category;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryFilter {
    public static final String FIELD_NAME = "name";
    public static final String FIELD_USER = "user";
    public static final String FIELD_ENABLE = "enabled";
    private String name;
    private Long user;
    private Boolean enabled;
}

package com.hieuph.todosmanagement.Filter.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserFilter {
    public static final String FIELD_NAME = "username";

    public static final String FIELD_email = "email";

    private String username;

    private String email;
}

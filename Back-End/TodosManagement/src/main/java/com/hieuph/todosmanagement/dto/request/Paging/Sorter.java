package com.hieuph.todosmanagement.dto.request.Paging;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Sorter {
    private String by;
    private String name;
}

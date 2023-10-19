package com.hieuph.todosmanagement.dto.request.Paging;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PagingRequest {
    private Integer page;

    private Integer limit;

    private Sorter sorter;
}

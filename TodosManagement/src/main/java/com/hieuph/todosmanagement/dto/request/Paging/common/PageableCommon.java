package com.hieuph.todosmanagement.dto.request.Paging.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Setter
@Getter
@NoArgsConstructor
public class PageableCommon {
    private Integer page;

    private Integer limit;

    private Integer totalPage;

    private SortCommon sort;

    public void updateTotalPage(Integer count) {
        if (count != null && limit != null) {
            totalPage = (int) Math.ceil(count / limit);
        }
    }

    public void initialize() {
        if (page == null || page < 1) {
            page = 1;
        }

        if (limit == null) {
            limit = ConstantCommon.LIMIT;
        }

        if (sort != null) {
            if (StringUtils.isEmpty(sort.getFileName())) {
                sort = null;
            } else {
                if (StringUtils.isEmpty(sort.getOrderBy())) {
                    sort.setOrderBy(ConstantCommon.SORT_DESC);
                }
            }
        }
    }

}

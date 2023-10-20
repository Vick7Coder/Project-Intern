package com.hieuph.todosmanagement.dto.request.Paging;

import com.hieuph.todosmanagement.dto.request.Paging.common.ConstantCommon;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

public class Pagination {
    private static final int PAGE = 1;
    private static final int LIMIT = 5;

    public static Pageable initPageable(PagingRequest pagingRequest) {
        Pageable pageable = null;

        if (pagingRequest != null) {

            if (pagingRequest.getPage() == null || pagingRequest.getPage() <= 0) {
                pagingRequest.setPage(PAGE);
            }

            if (pagingRequest.getLimit() == null) {
                pagingRequest.setLimit(LIMIT);
            }

            pagingRequest.setPage(pagingRequest.getPage() - 1);

            pageable = PageRequest.of(pagingRequest.getPage(), pagingRequest.getLimit());

            if (pagingRequest.getSorter() != null) {

                Sorter sortRequest = pagingRequest.getSorter();

                if (StringUtils.hasText(sortRequest.getName())) {

                    if (!StringUtils.hasText(sortRequest.getBy())) {
                        sortRequest.setBy(ConstantCommon.SORT_ASC.toString());
                    }

                    pageable = PageRequest.of(pagingRequest.getPage(), pagingRequest.getLimit(), initSort(sortRequest));
                }

            }

        } else {
            pageable = PageRequest.of(PAGE - 1, LIMIT);
        }

        return pageable;
    }

    public static Sort initSort(Sorter sorter) {
        Sort sort = Sort.by(sorter.getName());

        if (sorter.getBy().equals(ConstantCommon.SORT_ASC.toString())) {
            sort = sort.ascending();
        } else {
            sort = sort.descending();
        }
        return sort;
    }
}

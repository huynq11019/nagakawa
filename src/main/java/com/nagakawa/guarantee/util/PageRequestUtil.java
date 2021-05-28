package com.nagakawa.guarantee.util;

public final class PageRequestUtil {
    private PageRequestUtil() {}

   /* public static Pageable getPageAndSort(int page, int size, String sort, Map<String, String> allowSortColumn) {

        if (page < 1){
            page = 1;
        }

        if (size < 1) {
            size = Constants.PAGING.ITEM_PER_PAGE;
        }

        if (StringUtils.isEmpty(sort)) {
            return PageRequest.of(page - 1, size);
        }

        String field = sort;
        boolean isDescSort = sort.startsWith("-");
        if (isDescSort) {
            field = sort.substring(1);
        }

        if (CollectionUtils.isEmpty(allowSortColumn)) {
            return PageRequest.of(page - 1, size, isDescSort
                    ? Sort.by(sort).descending()
                    : Sort.by(sort).ascending());
        }

        if (allowSortColumn.containsKey(field)) {
            return PageRequest.of(page - 1, size, isDescSort
                    ? Sort.by(allowSortColumn.get(field)).descending()
                    : Sort.by(allowSortColumn.get(field)).ascending());
        }
        return PageRequest.of(page - 1, size);
    }*/
}

package com.pgy.rest;

import java.util.Collections;
import java.util.List;

/**
 * The page helper.
 *
 * @author Felix
 */
public class PageHelper {
    public static <T> List<T> pageItems(List<T> list, int pageNo, int pageSize) {
        int fromIndex = (pageNo - 1) * pageSize;
        if (fromIndex >= list.size()) {
            return Collections.emptyList();
        }
        int toIndex = Math.min(pageNo * pageSize, list.size());
        return list.subList(fromIndex, toIndex);
    }
}

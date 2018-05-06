package com.pgy.rest;

import java.util.List;

import com.google.common.base.Function;
import com.pgy.common.CollectionHelper;

/**
 * Rest response factory.
 *
 * @author Felix
 */
public class RestResponseFactory {

    private static final RestResponse SUCCESS_EMPTY_RESPONSE = newSuccessfulResponse(null);

    public static <T> RestResponse<T, ?, ?> newSuccessfulResponse(T result) {
        return new RestResponse.Builder<T, Object, Object>()
                .withSuccess(true)
                .withResult(result)
                .withMessage(null)
                .withPage(null)
                .build();
    }

    public static <T> RestResponse<?, ?, T> newPagedResponse(RestPage<T> page) {
        return new RestResponse.Builder<Object, Object, T>()
                .withSuccess(true)
                .withResult(null)
                .withMessage(null)
                .withPage(page)
                .build();
    }

    public static <T> RestResponse<T, ?, ?> newSuccessfulEmptyResponse() {
        return SUCCESS_EMPTY_RESPONSE;
    }

    public static <F, T> RestPageResponse<T> newTransformedPagedResponse(List<F> fromList, BasePagedRequest request,
            Function<F, T> function) {
        int count = fromList.size();
        List<T> toList = CollectionHelper.transform(fromList, function);
        toList = CollectionHelper.sort(toList, request.getOrderBy(), request.getOrder().isReverse());
        return newPagedResponse(new RestPage.Builder<T>()
                .withBasePagedRequest(request)
                .withTotalCount(count)
                .withResult(PageHelper.pageItems(toList, request.getPageNo(), request.getPageSize()))
                .build());
    }

    public static <M> RestResponse newFailedResponse(M message) {
        return new RestResponse.Builder<Object, M, Object>()
                .withSuccess(false)
                .withResult(null)
                .withMessage(message)
                .withPage(null)
                .build();
    }

    public static RestResponse newGlobalFailedResponse(String globalMessage) {
        return newFailedResponse(new GlobalMessage(globalMessage));
    }

}

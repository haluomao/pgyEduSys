package com.pgy.controller.grade;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.pgy.common.CollectionHelper;
import com.pgy.common.LogMessageBuilder;
import com.pgy.controller.bean.IdRequest;
import com.pgy.controller.bean.RestResultResponse;
import com.pgy.controller.grade.bean.GradePagedRequest;
import com.pgy.controller.grade.bean.GradeVO;
import com.pgy.grade.GradeManager;
import com.pgy.grade.bean.Grade;
import com.pgy.rest.PageHelper;
import com.pgy.rest.RestPage;
import com.pgy.rest.RestPageResponse;
import com.pgy.rest.RestResponseFactory;

/**
 * The impl of {@link GradeController}.
 *
 * @author Felix
 */
@RestController
public class GradeControllerImpl implements GradeController {
    private static final Log log = LogFactory.getLog(GradeControllerImpl.class);

    private final GradeManager gradeManager;

    @Autowired
    public GradeControllerImpl(GradeManager gradeManager) {
        this.gradeManager = gradeManager;
    }

    @Override
    public RestPageResponse<GradeVO> list(@RequestBody GradePagedRequest request) throws Exception {
        log.info(new LogMessageBuilder("list grade")
                .withParameter("request", request)
                .build());

        List<Grade> categories = gradeManager.getGrade(Collections.EMPTY_LIST);
        return RestResponseFactory.newPagedResponse(new RestPage.Builder<GradeVO>()
                .withBasePagedRequest(request)
                .withTotalCount(categories.size())
                .withResult(CollectionHelper.transform(
                        PageHelper.pageItems(categories, request.getPageNo(), request.getPageSize()),
                        new Function<Grade, GradeVO>() {
                            @Override
                            public GradeVO apply(Grade input) {
                                return GradeVO.Builder.aGradeRequest()
                                        .withGrade(input)
                                        .build();
                            }
                        }
                ))
                .build());
    }

    @Override
    public RestResultResponse<List<GradeVO>> all() throws Exception {
        log.info(new LogMessageBuilder("list all categories")
                .build());

        List<Grade> categories = gradeManager.getGrade(Collections.EMPTY_LIST);
        return RestResponseFactory.newSuccessfulResponse(CollectionHelper.transform(categories,
                new Function<Grade, GradeVO>() {
                    @Override
                    public GradeVO apply(Grade input) {
                        return GradeVO.Builder.aGradeRequest()
                                .withGrade(input)
                                .build();
                    }
                }));
    }

    @Override
    public RestResultResponse<GradeVO> detail(@RequestBody IdRequest idRequest) throws Exception {
        log.info(new LogMessageBuilder("get the detail of a grade")
                .withParameter("request", idRequest)
                .build());
        Preconditions.checkArgument(idRequest.getId() > 0);
        Grade grade = gradeManager.detail(idRequest.getId());

        return RestResponseFactory.newSuccessfulResponse(
                GradeVO.Builder.aGradeRequest()
                        .withGrade(grade)
                        .build());
    }

    @Override
    public RestResultResponse<Void> create(@RequestBody GradeVO request) throws Exception {
        log.info(new LogMessageBuilder("create grade")
                .withParameter("request", request)
                .build());

        gradeManager.create(request.buildGrade());
        return RestResponseFactory.newSuccessfulEmptyResponse();
    }

    @Override
    public RestResultResponse<Void> update(@RequestBody GradeVO request) throws Exception {
        log.info(new LogMessageBuilder("update grade")
                .withParameter("request", request)
                .build());

        Preconditions.checkArgument(request.getId() > 0);
        gradeManager.update(request.buildGrade());
        return RestResponseFactory.newSuccessfulEmptyResponse();
    }

    @Override
    public RestResultResponse<Void> delete(@RequestBody IdRequest idRequest) throws Exception {
        log.info(new LogMessageBuilder("delete grade")
                .withParameter("request", idRequest)
                .build());

        Preconditions.checkArgument(idRequest.getId() > 0);
        gradeManager.delete(Lists.newArrayList(idRequest.getId()));
        return RestResponseFactory.newSuccessfulEmptyResponse();
    }
}

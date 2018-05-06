package com.pgy.controller.grade;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;

import com.pgy.controller.bean.IdRequest;
import com.pgy.controller.bean.RestResultResponse;
import com.pgy.controller.grade.bean.GradePagedRequest;
import com.pgy.controller.grade.bean.GradeVO;
import com.pgy.rest.RestPageResponse;

/**
 * The grade controller.
 *
 * @author Felix
 */
@RequestMapping("/api/grade")
public interface GradeController {

    @RequestMapping("/list")
    RestPageResponse<GradeVO> list(GradePagedRequest request) throws Exception;

    @RequestMapping("/all")
    RestResultResponse<List<GradeVO>> all() throws Exception;

    @RequestMapping("/detail")
    RestResultResponse<GradeVO> detail(IdRequest idRequest) throws Exception;

    @RequestMapping("/create")
    RestResultResponse<Void> create(GradeVO request) throws Exception;

    @RequestMapping("/update")
    RestResultResponse<Void> update(GradeVO request) throws Exception;

    @RequestMapping("/delete")
    RestResultResponse<Void> delete(IdRequest idRequest) throws Exception;
}

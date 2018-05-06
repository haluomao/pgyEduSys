package com.pgy.controller.material;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;

import com.pgy.controller.bean.IdRequest;
import com.pgy.controller.bean.RestResultResponse;
import com.pgy.controller.material.bean.MaterialPagedRequest;
import com.pgy.controller.material.bean.MaterialVO;
import com.pgy.rest.RestPageResponse;

/**
 * The category controller.
 *
 * @author Felix
 */
@RequestMapping("/api/material")
public interface MaterialController {

    @RequestMapping("/list")
    RestPageResponse<MaterialVO> list(MaterialPagedRequest request) throws Exception;

    @RequestMapping("/all")
    RestResultResponse<List<MaterialVO>> all() throws Exception;

    @RequestMapping("/detail")
    RestResultResponse<MaterialVO> detail(IdRequest idRequest) throws Exception;

    @RequestMapping("/create")
    RestResultResponse<Void> create(MaterialVO request) throws Exception;

    @RequestMapping("/update")
    RestResultResponse<Void> update(MaterialVO request) throws Exception;

    @RequestMapping("/delete")
    RestResultResponse<Void> delete(IdRequest idRequest) throws Exception;
}

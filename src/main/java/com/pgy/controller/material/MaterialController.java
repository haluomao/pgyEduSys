package com.pgy.controller.material;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;

import com.pgy.auth.bean.CustomUser;
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
@RequestMapping("/api/v1/material")
public interface MaterialController {

    @RequestMapping("/list")
    RestPageResponse<MaterialVO> list(CustomUser loginUser, MaterialPagedRequest request) throws Exception;

    @RequestMapping("/all")
    RestResultResponse<List<MaterialVO>> all(CustomUser loginUser) throws Exception;

    @RequestMapping("/detail")
    RestResultResponse<MaterialVO> detail(CustomUser loginUser, IdRequest idRequest) throws Exception;

    @RequestMapping("/create")
    RestResultResponse<Void> create(CustomUser loginUser, MaterialVO request) throws Exception;

    @RequestMapping("/update")
    RestResultResponse<Void> update(CustomUser loginUser, MaterialVO request) throws Exception;

    @RequestMapping("/delete")
    RestResultResponse<Void> delete(CustomUser loginUser, IdRequest idRequest) throws Exception;
}

package com.hebutgo.ework.controller;


import com.hebutgo.ework.common.ApiResponse;
import com.hebutgo.ework.common.CommonConstant;
import com.hebutgo.ework.common.ErrorCodeEnum;
import com.hebutgo.ework.common.exception.BizException;
import com.hebutgo.ework.entity.GroupAdmin;
import com.hebutgo.ework.entity.request.JoinGroupRequest;
import com.hebutgo.ework.entity.vo.JoinGroupVo;
import com.hebutgo.ework.service.IGroupAdminService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 小组的管理员表 前端控制器
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-22
 */
@RestController
@RequestMapping("/ework/group-admin")
public class GroupAdminController {

    Logger logger = LoggerFactory.getLogger(GroupAdmin.class);

    @Autowired
    IGroupAdminService iGroupAdminService;

    @CrossOrigin
    @ApiOperation(value = "管理员加入小组", tags = CommonConstant.ADMIN_GROUP)
    @PostMapping("/joinGroup")
    public ApiResponse<JoinGroupVo> joinGroup(
            @RequestBody JoinGroupRequest joinGroupRequest
    ) {
        JoinGroupVo joinGroupVo;
        try {
            joinGroupVo = iGroupAdminService.joinGroup(joinGroupRequest);
        } catch (BizException e) {
            logger.error("管理员加入小组失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("管理员加入小组失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        return ApiResponse.success(joinGroupVo);
    }

    @CrossOrigin
    @ApiOperation(value = "管理员退出小组", tags = CommonConstant.ADMIN_GROUP)
    @PostMapping("/quitGroup")
    public ApiResponse<JoinGroupVo> quitGroup(
            @RequestBody JoinGroupRequest joinGroupRequest
    ) {
        JoinGroupVo joinGroupVo;
        try {
            joinGroupVo = iGroupAdminService.quitGroup(joinGroupRequest);
        } catch (BizException e) {
            logger.error("管理员退出小组失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("管理员退出小组失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        return ApiResponse.success(joinGroupVo);
    }
}

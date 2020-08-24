package com.hebutgo.ework.controller;


import com.hebutgo.ework.common.ApiResponse;
import com.hebutgo.ework.common.CommonConstant;
import com.hebutgo.ework.common.ErrorCodeEnum;
import com.hebutgo.ework.common.exception.BizException;
import com.hebutgo.ework.entity.GroupInfo;
import com.hebutgo.ework.entity.request.ChangeGroupRequest;
import com.hebutgo.ework.entity.request.CreateGroupRequest;
import com.hebutgo.ework.entity.vo.CreateGroupVo;
import com.hebutgo.ework.service.IGroupInfoService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 小组信息表 前端控制器
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-24
 */
@RestController
@RequestMapping("/ework/group-info")
public class GroupInfoController {

    Logger logger = LoggerFactory.getLogger(GroupInfo.class);

    @Autowired
    IGroupInfoService iGroupInfoService;

    @CrossOrigin
    @ApiOperation(value = "管理员建立小组",tags = CommonConstant.GROUP_ACCOUNT)
    @PostMapping("/create")
    public ApiResponse<CreateGroupVo> create(
            @RequestBody CreateGroupRequest createGroupRequest
    ){
        CreateGroupVo createGroupVo;
        try{
            createGroupVo = iGroupInfoService.create(createGroupRequest);
        }catch (BizException e) {
            logger.error("新建小组失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("新建小组失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        return ApiResponse.success(createGroupVo);
    }

    @CrossOrigin
    @ApiOperation(value = "管理员修改小组信息",tags = CommonConstant.GROUP_ACCOUNT)
    @PostMapping("/change")
    public ApiResponse<CreateGroupVo> change(
            @RequestBody ChangeGroupRequest changeGroupRequest
            ){
        CreateGroupVo createGroupVo;
        try{
            createGroupVo = iGroupInfoService.change(changeGroupRequest);
        }catch (BizException e) {
            logger.error("修改小组信息失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("修改小组信息失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        return ApiResponse.success(createGroupVo);
    }
}

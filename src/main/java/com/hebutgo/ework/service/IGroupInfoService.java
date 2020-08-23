package com.hebutgo.ework.service;

import com.hebutgo.ework.entity.GroupInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hebutgo.ework.entity.request.CreateGroupRequest;
import com.hebutgo.ework.entity.vo.CreateGroupVo;

/**
 * <p>
 * 小组信息表 服务类
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-24
 */
public interface IGroupInfoService extends IService<GroupInfo> {
    CreateGroupVo create(CreateGroupRequest createGroupRequest);
}

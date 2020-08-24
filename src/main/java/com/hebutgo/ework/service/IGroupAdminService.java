package com.hebutgo.ework.service;

import com.hebutgo.ework.entity.GroupAdmin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hebutgo.ework.entity.request.JoinGroupRequest;
import com.hebutgo.ework.entity.vo.JoinGroupVo;

/**
 * <p>
 * 小组的管理员表 服务类
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-22
 */
public interface IGroupAdminService extends IService<GroupAdmin> {
    public JoinGroupVo joinGroup(JoinGroupRequest joinGroupRequest);
    public JoinGroupVo quitGroup(JoinGroupRequest joinGroupRequest);
}

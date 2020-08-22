package com.hebutgo.ework.service.impl;

import com.hebutgo.ework.entity.Group;
import com.hebutgo.ework.mapper.GroupMapper;
import com.hebutgo.ework.service.IGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 小组信息表 服务实现类
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-22
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements IGroupService {

}

package com.hebutgo.ework.service.impl;

import com.hebutgo.ework.entity.Admin;
import com.hebutgo.ework.mapper.AdminMapper;
import com.hebutgo.ework.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员（教师）表 服务实现类
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-22
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

}

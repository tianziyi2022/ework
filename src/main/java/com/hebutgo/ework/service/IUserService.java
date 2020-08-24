package com.hebutgo.ework.service;

import com.hebutgo.ework.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hebutgo.ework.entity.request.*;
import com.hebutgo.ework.entity.vo.*;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-22
 */
public interface IUserService extends IService<User> {
    public RegisterVo register(UserRegisterRequest userRegisterRequest);
    public LoginVo login(LoginRequest loginRequest);
    public ChangeDetailVo changeDetail(ChangeDetailRequest changeDetailRequest);
    public LogoutVo quit(LogoutRequest logoutRequest);
    public LogoutVo logout(LogoutRequest logoutRequest);
    public JoinGroupVo joinGroup(JoinGroupRequest joinGroupRequest);
    public JoinGroupVo quitGroup(JoinGroupRequest joinGroupRequest);
    public UserDetailVo detail(AccountDetailRequest accountDetailRequest);
}

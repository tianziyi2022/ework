package com.hebutgo.ework.service;

import com.hebutgo.ework.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hebutgo.ework.entity.request.ChangeDetailRequest;
import com.hebutgo.ework.entity.request.LoginRequest;
import com.hebutgo.ework.entity.request.LogoutRequest;
import com.hebutgo.ework.entity.request.UserRegisterRequest;
import com.hebutgo.ework.entity.vo.ChangeDetailVo;
import com.hebutgo.ework.entity.vo.LoginVo;
import com.hebutgo.ework.entity.vo.LogoutVo;
import com.hebutgo.ework.entity.vo.RegisterVo;

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
}

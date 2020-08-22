package com.hebutgo.ework.common;

import lombok.Getter;

/**
 * 错误码枚举
 * @author zuozhiwei
 */
public enum ErrorCodeEnum {
    /**
     * 系统发生异常
     */
    SYSTEM_DEFAULT_ERROR(1, "对不起，系统发生了异常，请您反馈给平台"),
    /**
     * token验证错误
     */
    INVALID_TOKEN (401, "非法token"),
    ;

    /**
     * 构造函数
     * @param code
     * @param msg
     */
    ErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 错误码
     */
    @Getter
    private int code;

    /**
     * 错误信息
     */
    @Getter
    private String msg;
}

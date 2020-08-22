package com.hebutgo.ework.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zuozhiwei
 */
@Data
@ApiModel(value="基础返回类",description="基础返回类")
public final class ApiResponse<T> {
    /**
     * 1 成功
     */
    public static final int SUCCESS_CODE = 0;
    /**
     * 0 失败
     */
    public static final int ERROR_CODE = 1;



    /**
     * 成功
     */
    private static final ApiResponse SUCCESS = new ApiResponse<>(SUCCESS_CODE, "SUCCESS", null);
    /**
     * 失败
     */
    private static final ApiResponse ERROR = new ApiResponse<>(ERROR_CODE, "ERROR", null);

    /**
     * 返回状态码
     */
    @Getter
    @Setter
    @ApiModelProperty(example="1",allowableValues = "0,1",notes = "0成功，1失败")
    private int code;

    /**
     * 提示信息
     */
    @Getter
    @Setter
    @ApiModelProperty(example="参数错误",notes = "错误信息")
    private String msg;

    /**
     * 数据
     */
    @Getter
    @Setter
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(int code, String msg, T data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
        if (code != 0) {
        }

    }

    public boolean isSuccess() {
        return code == 0;
    }

    public static ApiResponse error(ErrorCodeEnum errorCodeEnum) {
        return error(errorCodeEnum.getCode(), errorCodeEnum.getMsg(), null);
    }

    public static ApiResponse error(int code, String msg, Object data) {
        return new ApiResponse<>(code, msg, data);
    }

    public static ApiResponse error(String msg, Object data) {
        return error(ERROR.getCode(), msg, data);
    }

    public static ApiResponse error(Object data) {
        return error(ERROR.getMsg(), data);
    }

    public static ApiResponse error(String msg) {
        return error(msg, null);
    }

    public static <T> ApiResponse<T> success(int code, String msg, T data) {
        return new ApiResponse<>(code, msg, data);
    }

    public static <T> ApiResponse<T> success(String msg, T data) {
        return success(SUCCESS.getCode(), msg, data);
    }

    public static <T> ApiResponse<T> success(T data) {
        return success(SUCCESS.getMsg(), data);
    }

    public static ApiResponse success(String msg) {
        return success(msg, null);
    }

    public static ApiResponse success() {
        return success(SUCCESS.code, SUCCESS.msg, null);
    }

    public static ApiResponse error() {
        return error(ERROR.code, ERROR.msg, null);
    }
}

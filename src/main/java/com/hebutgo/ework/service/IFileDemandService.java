package com.hebutgo.ework.service;

import com.hebutgo.ework.entity.FileDemand;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hebutgo.ework.entity.request.FileUploadRequest;
import com.hebutgo.ework.entity.vo.FileUploadVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 作业要求文件上传表 服务类
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-26
 */
public interface IFileDemandService extends IService<FileDemand> {
    public FileUploadVo upload(FileUploadRequest fileUploadRequest);
    public FileUploadVo upload(FileUploadRequest fileUploadRequest,MultipartFile multipartFile);
    public FileUploadVo upload(MultipartFile multipartFile);
}

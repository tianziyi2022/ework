package com.hebutgo.ework.service;

import com.hebutgo.ework.entity.FileSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hebutgo.ework.entity.request.FileUploadRequest;
import com.hebutgo.ework.entity.vo.FileUploadVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 提交作业中的文件上传表 服务类
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-26
 */
public interface IFileSubmitService extends IService<FileSubmit> {
    public FileUploadVo upload(FileUploadRequest fileUploadRequest);
    public FileUploadVo upload(FileUploadRequest fileUploadRequest,MultipartFile multipartFile);
    public FileUploadVo upload(MultipartFile multipartFile);
    public String getUrl(Integer id);
}

package com.hebutgo.ework.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hebutgo.ework.common.exception.BizException;
import com.hebutgo.ework.common.utils.FileSubmitUtil;
import com.hebutgo.ework.entity.Admin;
import com.hebutgo.ework.entity.FileDemand;
import com.hebutgo.ework.entity.FileSubmit;
import com.hebutgo.ework.entity.User;
import com.hebutgo.ework.entity.request.FileUploadRequest;
import com.hebutgo.ework.entity.vo.FileUploadVo;
import com.hebutgo.ework.mapper.FileSubmitMapper;
import com.hebutgo.ework.mapper.UserMapper;
import com.hebutgo.ework.service.IFileSubmitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * <p>
 * 提交作业中的文件上传表 服务实现类
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-26
 */
@Service
public class FileSubmitServiceImpl extends ServiceImpl<FileSubmitMapper, FileSubmit> implements IFileSubmitService {
    
    @Resource
    FileSubmitMapper fileSubmitMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    FileSubmitUtil fileSubmitUtil;

    @Override
    public FileUploadVo upload(FileUploadRequest fileUploadRequest) {
        if(fileUploadRequest.getType()!=20){
            throw new BizException("用户类型错误");
        }
        User user = userMapper.selectById(fileUploadRequest.getId());
        if(Objects.isNull(user)){
            throw new BizException("用户不存在");
        }
        if(user.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!Objects.equals(user.getToken(), fileUploadRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        MultipartFile file = fileUploadRequest.getFile();
        if(Objects.isNull(file)||file.isEmpty()){
            throw new BizException("上传失败，未选择文件");
        }
        String fileName = file.getOriginalFilename() + "-" + user.getUserName() + "-" + (System.currentTimeMillis()%100000000);
        String path = "/files/work/demand/";
        File dest = new File(path + fileName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new BizException("上传失败");
        }
        FileSubmit fileSubmit = FileSubmit.builder().build();
        fileSubmit.setFileName(fileName);
        fileSubmit.setUrl(path + fileName);
        fileSubmit.setUserId(fileUploadRequest.getId());
        fileSubmitMapper.insert(fileSubmit);
        QueryWrapper<FileSubmit> fileSubmitQueryWrapper = new QueryWrapper<>();
        fileSubmitQueryWrapper.setEntity(fileSubmit);
        FileSubmit fileSubmit1 = fileSubmitMapper.selectOne(fileSubmitQueryWrapper);
        FileUploadVo fileUploadVo = new FileUploadVo();
        fileUploadVo.setId(fileSubmit1.getId());
        fileUploadVo.setUrl(fileSubmit1.getUrl());
        fileUploadVo.setTopic("上传成功");
        return fileUploadVo;
    }

    @Override
    public FileUploadVo upload(FileUploadRequest fileUploadRequest, MultipartFile multipartFile) {
        fileUploadRequest.setFile(multipartFile);
        if(fileUploadRequest.getType()!=20){
            throw new BizException("用户类型错误");
        }
        User user = userMapper.selectById(fileUploadRequest.getId());
        if(Objects.isNull(user)){
            throw new BizException("用户不存在");
        }
        if(user.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!Objects.equals(user.getToken(), fileUploadRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        MultipartFile file = fileUploadRequest.getFile();
        if(Objects.isNull(file)||file.isEmpty()){
            throw new BizException("上传失败，未选择文件");
        }
        String fileName = file.getOriginalFilename() + "-" + user.getUserName() + "-" + (System.currentTimeMillis()%100000000);
        String path = "/files/work/demand/";
        File dest = new File(path + fileName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new BizException("上传失败");
        }
        FileSubmit fileSubmit = FileSubmit.builder().build();
        fileSubmit.setFileName(fileName);
        fileSubmit.setUrl(path + fileName);
        fileSubmit.setUserId(fileUploadRequest.getId());
        fileSubmitMapper.insert(fileSubmit);
        QueryWrapper<FileSubmit> fileSubmitQueryWrapper = new QueryWrapper<>();
        fileSubmitQueryWrapper.setEntity(fileSubmit);
        FileSubmit fileSubmit1 = fileSubmitMapper.selectOne(fileSubmitQueryWrapper);
        FileUploadVo fileUploadVo = new FileUploadVo();
        fileUploadVo.setId(fileSubmit1.getId());
        fileUploadVo.setUrl(fileSubmit1.getUrl());
        fileUploadVo.setTopic("上传成功");
        return fileUploadVo;
    }

    @Override
    public FileUploadVo upload(MultipartFile multipartFile) {
        return fileSubmitUtil.storeFileInDatabase(multipartFile,null);
    }

    @Override
    public String getUrl(Integer id) {
        FileSubmit fileSubmit = fileSubmitMapper.selectById(id);
        if(Objects.isNull(fileSubmit)||fileSubmit.getIsDelete()==1){
            throw new BizException("文件不存在");
        }
        return fileSubmit.getUrl();
    }
}

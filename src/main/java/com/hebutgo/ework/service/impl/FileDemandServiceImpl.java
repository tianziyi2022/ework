package com.hebutgo.ework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hebutgo.ework.common.exception.BizException;
import com.hebutgo.ework.entity.Admin;
import com.hebutgo.ework.entity.FileDemand;
import com.hebutgo.ework.entity.request.FileUploadRequest;
import com.hebutgo.ework.entity.vo.FileUploadVo;
import com.hebutgo.ework.mapper.AdminMapper;
import com.hebutgo.ework.mapper.FileDemandMapper;
import com.hebutgo.ework.service.IFileDemandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * <p>
 * 作业要求文件上传表 服务实现类
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-26
 */
@Service
public class FileDemandServiceImpl extends ServiceImpl<FileDemandMapper, FileDemand> implements IFileDemandService {

    @Resource
    FileDemandMapper fileDemandMapper;

    @Resource
    AdminMapper adminMapper;

    @Override
    public FileUploadVo upload(FileUploadRequest fileUploadRequest) {
        if(fileUploadRequest.getType()!=10){
            throw new BizException("用户类型错误");
        }
        Admin admin = adminMapper.selectById(fileUploadRequest.getId());
        if(Objects.isNull(admin)){
            throw new BizException("用户不存在");
        }
        if(admin.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!Objects.equals(admin.getToken(), fileUploadRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        MultipartFile file = fileUploadRequest.getFile();
        if(Objects.isNull(file)||file.isEmpty()){
            throw new BizException("上传失败，未选择文件");
        }
        String fileName = file.getOriginalFilename() + "-" + admin.getUserName() + "-" + (System.currentTimeMillis()%100000000);
        String path = "/files/work/demand/";
        File dest = new File(path + fileName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new BizException("上传失败");
        }
        FileDemand fileDemand = new FileDemand();
        fileDemand.setFileName(fileName);
        fileDemand.setUrl(path + fileName);
        fileDemand.setAdminId(fileUploadRequest.getId());
        fileDemandMapper.insert(fileDemand);
        QueryWrapper<FileDemand> fileDemandQueryWrapper = new QueryWrapper<>();
        fileDemandQueryWrapper.setEntity(fileDemand);
        FileDemand fileDemand1 = fileDemandMapper.selectOne(fileDemandQueryWrapper);
        FileUploadVo fileUploadVo = new FileUploadVo();
        fileUploadVo.setId(fileDemand1.getId());
        fileUploadVo.setUrl(fileDemand1.getUrl());
        fileUploadVo.setTopic("上传成功");
        return fileUploadVo;
    }

    @Override
    public FileUploadVo upload(FileUploadRequest fileUploadRequest, MultipartFile multipartFile) {
        fileUploadRequest.setFile(multipartFile);
        if(fileUploadRequest.getType()!=10){
            throw new BizException("用户类型错误");
        }
        Admin admin = adminMapper.selectById(fileUploadRequest.getId());
        if(Objects.isNull(admin)){
            throw new BizException("用户不存在");
        }
        if(admin.getStatus()!=10) {
            throw new BizException("用户状态异常");
        }
        if(!Objects.equals(admin.getToken(), fileUploadRequest.getToken())){
            throw new BizException("未登陆或登陆超时");
        }
        MultipartFile file = fileUploadRequest.getFile();
        if(Objects.isNull(file)||file.isEmpty()){
            throw new BizException("上传失败，未选择文件");
        }
        String fileName = file.getOriginalFilename() + "-" + admin.getUserName() + "-" + (System.currentTimeMillis()%100000000);
        String path = "/files/work/demand/";
        File dest = new File(path + fileName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new BizException("上传失败");
        }
        FileDemand fileDemand = new FileDemand();
        fileDemand.setFileName(fileName);
        fileDemand.setUrl(path + fileName);
        fileDemand.setAdminId(fileUploadRequest.getId());
        fileDemandMapper.insert(fileDemand);
        QueryWrapper<FileDemand> fileDemandQueryWrapper = new QueryWrapper<>();
        fileDemandQueryWrapper.setEntity(fileDemand);
        FileDemand fileDemand1 = fileDemandMapper.selectOne(fileDemandQueryWrapper);
        FileUploadVo fileUploadVo = new FileUploadVo();
        fileUploadVo.setId(fileDemand1.getId());
        fileUploadVo.setUrl(fileDemand1.getUrl());
        fileUploadVo.setTopic("上传成功");
        return fileUploadVo;
    }
}

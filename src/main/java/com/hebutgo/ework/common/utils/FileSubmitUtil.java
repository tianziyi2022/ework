package com.hebutgo.ework.common.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hebutgo.ework.common.exception.FileException;
import com.hebutgo.ework.entity.FileSubmit;
import com.hebutgo.ework.entity.vo.FileUploadVo;
import com.hebutgo.ework.mapper.FileSubmitMapper;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author zuozhiwei
 *
 * 在调用以下接口时候都需要在类中声明如下两条语句
 * @javax.annotation.Resource
 * FileUtil fileUtil;
 */
@Component
public class FileSubmitUtil {

    Logger logger = LoggerFactory.getLogger(FileSubmitUtil.class);

    @Value("/Files/Submit/Upload")
    String uploadPath;

    @Value("/Files/Submit/Domain")
    String domain;

    @Resource
    FileSubmitMapper fileSubmitMapper;

    /**
     * 文件在本地存储的地址
     */
    private Path fileStorageLocation = null;

    /**
     * 存储文件到系统
     * @param file 文件
     * @param folder 文件夹 默认不传，可以传null
     * @return 文件信息
     */
    public FileInfo storeFile(MultipartFile file, String folder) {
        this.getLocation();
        // 完整文件名
        String originName = StringUtils.cleanPath(file.getOriginalFilename());
        // 文件名后缀，最后一个.后面的
        String ext = originName.substring(originName.lastIndexOf("."));
        // 文件名拼接
        String fileName = originName.substring(0,originName.lastIndexOf(".")) + "-" + System.currentTimeMillis() + ext;

        if (folder != null && !"".equals(folder)) {
            folder = clearFilePath(folder);
            fileName = folder + "/" + fileName;
        }

        FileInfo fileInfo = new FileInfo();
        fileInfo.setOriginName(originName);
        fileInfo.setStorageFileName(fileName);
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileInfo;
        } catch (IOException ex) {
            throw new FileException("Could not store file " + originName + ". Please try again!", ex);
        }
    }

    /**
     * 存储文件到系统
     * @param file 文件
     * @return 文件id
     */
    public FileUploadVo storeFileInDatabase(MultipartFile file, String note/*, Integer userId*/) {
        this.getLocation();
        // 完整文件名
        String originName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // 文件名后缀，最后一个.后面的
        String ext = originName.substring(originName.lastIndexOf("."));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String folder = "user_" + sdf.format(new Date()).substring(4, 8);
        // 文件名拼接
        String fileName = originName.substring(0,originName.lastIndexOf(".")) + "-" + System.currentTimeMillis() + ext;
        folder = clearFilePath(folder);
        fileName = folder + "/" + fileName;
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            InputStream inputStream = file.getInputStream();
//            System.out.println(targetLocation);
//            Files.createFile(targetLocation);
            final Path tmp = targetLocation.getParent();
            if(tmp!=null){
                Files.createDirectories(tmp);
            }
            targetLocation.toFile();
            Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
            if (note == null) {
                note = "";
            }
            FileSubmit fileSubmit = FileSubmit.builder().fileName(originName).url(fileName).build();
            System.out.println(fileSubmit);
            fileSubmitMapper.insert(fileSubmit);
            QueryWrapper<FileSubmit> fileSubmitQueryWrapper = new QueryWrapper<>();
            fileSubmitQueryWrapper.setEntity(fileSubmit);
            FileSubmit fileSubmit1 = fileSubmitMapper.selectOne(fileSubmitQueryWrapper);
            FileUploadVo fileUploadVo = new FileUploadVo();
            fileUploadVo.setId(fileSubmit1.getId());
            fileUploadVo.setUrl(fileSubmit1.getUrl());
            fileUploadVo.setTopic("上传成功");
            return fileUploadVo;
        } catch (IOException ex) {
            throw new FileException("Could not store file " + originName + ". Please try again!", ex);
        }
    }

    public String getUrlInFileId(Integer id) {
        FileSubmit fileSubmit = fileSubmitMapper.selectById(id);
        return getFileUrl(fileSubmit.getUrl());
    }

    /**
     * 获取文件的url
     * url拼接文件名，可直接访问文件
     * @param fileName
     * @return
     */
    public String getFileUrl(String fileName) {
        return domain+"/"+fileName;
    }

    /**
     * 清理路径中间的多余的/
     * @param source
     * @return
     */
    public String clearFilePath(String source) {
        String element = File.separator;
        source = StringUtil.trim(source,element);
        while (source.contains(element+element)) {
            source = source.replace(element+element,element);
        }
        return source;
    }

    /**
     * 创建对象
     */
    private void getLocation(){
        System.out.println(uploadPath);
        this.fileStorageLocation = Paths.get(uploadPath).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
}

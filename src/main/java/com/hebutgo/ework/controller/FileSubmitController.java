package com.hebutgo.ework.controller;


import com.hebutgo.ework.common.ApiResponse;
import com.hebutgo.ework.common.CommonConstant;
import com.hebutgo.ework.common.ErrorCodeEnum;
import com.hebutgo.ework.common.exception.BizException;
import com.hebutgo.ework.entity.FileSubmit;
import com.hebutgo.ework.entity.request.FileUploadRequest;
import com.hebutgo.ework.entity.vo.FileUploadVo;
import com.hebutgo.ework.service.IFileSubmitService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 提交作业中的文件上传表 前端控制器
 * </p>
 *
 * @author tianziyi
 * @since 2020-08-26
 */
@RestController
@RequestMapping("/ework/file-submit")
public class FileSubmitController {
    
    Logger logger = LoggerFactory.getLogger(FileSubmit.class);

    @Autowired
    IFileSubmitService iFileSubmitService;

    @CrossOrigin
    @ApiOperation(value = "文件上传（作业内容）",tags = CommonConstant.FILE_UPLOAD)
    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<FileUploadVo> upload(
            @RequestBody FileUploadRequest fileUploadRequest,
            @RequestParam("file") MultipartFile multipartfile
    ){
        FileUploadVo fileUploadVo;
        try{
            fileUploadVo = iFileSubmitService.upload(fileUploadRequest,multipartfile);
        }catch (BizException e) {
            logger.error("文件上传（作业内容）失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("文件上传（作业内容）失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("文件上传（作业内容）成功");
        return ApiResponse.success(fileUploadVo);
    }

    @CrossOrigin
    @ApiOperation(value = "文件上传（作业内容）",tags = CommonConstant.FILE_UPLOAD)
    @PostMapping(value = "/uploadFile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<FileUploadVo> uploadFile(
            @RequestParam("file") MultipartFile multipartfile
    ){
        FileUploadVo fileUploadVo;
        try{
            fileUploadVo = iFileSubmitService.upload(multipartfile);
        }catch (BizException e) {
            logger.error("文件上传（作业内容）失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("文件上传（作业内容）失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("文件上传（作业内容）成功");
        return ApiResponse.success(fileUploadVo);
    }

    @CrossOrigin
    @ApiOperation(value = "获取文件url",tags = CommonConstant.FILE_DOWNLOAD)
    @PostMapping(value = "/getUrl",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<String> getUrl(
            @RequestParam("id") Integer id
    ){
        String string;
        try{
            string = iFileSubmitService.getUrl(id);
        }catch (BizException e) {
            logger.error("获取文件url失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("获取文件url失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("获取文件url成功");
        return ApiResponse.success(string);
    }

    @CrossOrigin
    @ApiOperation(value = "下载文件",tags = CommonConstant.FILE_DOWNLOAD)
    @GetMapping(value = "/download",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Resource> download(
            @RequestParam("id") Integer id
    ){
        Resource resource;
        try{
            resource = iFileSubmitService.download(id);
        }catch (BizException e) {
            logger.error("下载文件失败", e);
            return ApiResponse.error(e.getErrMessage());
        } catch (Exception e) {
            logger.error("下载文件失败", e);
            return ApiResponse.error(ErrorCodeEnum.SYSTEM_DEFAULT_ERROR);
        }
        logger.info("下载文件成功");
        return ApiResponse.success(resource);
    }
}

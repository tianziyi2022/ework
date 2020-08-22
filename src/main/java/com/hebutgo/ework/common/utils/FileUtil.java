package com.hebutgo.ework.common.utils;

import org.springframework.stereotype.Component;

/**
 *
 * @author zuozhiwei
 *
 * 在调用以下接口时候都需要在类中声明如下两条语句
 * @javax.annotation.Resource
 * FileUtil fileUtil;
 */
@Component
public class FileUtil {
//
//    Logger logger = LoggerFactory.getLogger(FileUtil.class);
//
//    @Value("${file.uploadPath}")
//    String uploadPath;
//
//    @Value("${file.domain}")
//    String domain;
//
//    @Resource
//    SysFileMapper sysFileMapper;
//
//
//    /**
//     * 文件在本地存储的地址
//     */
//    private Path fileStorageLocation = null;
//
//
//    /**
//     * 存储文件到系统
//     * @param file 文件
//     * @param folder 文件夹 默认不传，可以传null
//     * @return 文件信息
//     */
//    public FileInfo storeFile(MultipartFile file, String folder) {
//        this.getLocation();
//        // 完整文件名
//        String originName = StringUtils.cleanPath(file.getOriginalFilename());
//        // 文件名后缀，最后一个.后面的
//        String ext = originName.substring(originName.lastIndexOf("."));
//        // 文件名拼接
//        String fileName = originName.substring(0,originName.lastIndexOf(".")) + "-" + System.currentTimeMillis() + ext;
//
//        if (folder != null && !"".equals(folder)) {
//            folder = clearFilePath(folder);
//            fileName = folder + "/" + fileName;
//        }
//
//        FileInfo fileInfo = new FileInfo();
//        fileInfo.setOriginName(originName);
//        fileInfo.setStorageFileName(fileName);
//        try {
//            // Check if the file's name contains invalid characters
//            if(fileName.contains("..")) {
//                throw new FileException("Sorry! Filename contains invalid path sequence " + fileName);
//            }
//            // Copy file to the target location (Replacing existing file with the same name)
//            Path targetLocation = this.fileStorageLocation.resolve(fileName);
//            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//
//            return fileInfo;
//        } catch (IOException ex) {
//            throw new FileException("Could not store file " + originName + ". Please try again!", ex);
//        }
//    }
//
//    /**
//     * 存储文件到系统
//     * @param file 文件
//     * @param folder 文件夹 默认不传，可以传null
//     * @return 文件id
//     */
//    public String storeFileInDatabase(MultipartFile file, String folder, String note) {
//        this.getLocation();
//        // 完整文件名
//        String originName = StringUtils.cleanPath(file.getOriginalFilename());
//        // 文件名后缀，最后一个.后面的
//        String ext = originName.substring(originName.lastIndexOf("."));
//        // 文件名拼接
//        String fileName = originName.substring(0,originName.lastIndexOf(".")) + "-" + System.currentTimeMillis() + ext;
//        if (folder != null && !"".equals(folder)) {
//            folder = clearFilePath(folder);
//            fileName = folder + "/" + fileName;
//        }
//        try {
//            // Check if the file's name contains invalid characters
//            if(fileName.contains("..")) {
//                throw new FileException("Sorry! Filename contains invalid path sequence " + fileName);
//            }
//            // Copy file to the target location (Replacing existing file with the same name)
//            Path targetLocation = this.fileStorageLocation.resolve(fileName);
//            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//            String fileId = "sf-"+ UUID.randomUUID();
//            if (note == null) {
//                note = "";
//            }
//            sysFileMapper.insert(
//                    SysFile.builder().sysFileId(fileId).name(originName).note(note).path(fileName).build()
//            );
//            return fileId;
//        } catch (IOException ex) {
//            throw new FileException("Could not store file " + originName + ". Please try again!", ex);
//        }
//    }
//
//    /**
//     * 通过文件id获取前端可访问的url
//     * @param fileId
//     * @return
//     */
//    public String getUrlInFileId(String fileId) {
//        SysFile sysFile = sysFileMapper.selectOne(
//                Wrappers.lambdaQuery(SysFile.class).eq(SysFile::getSysFileId,fileId)
//        );
//        return getFileUrl(sysFile.getPath());
//    }
//
//    /**
//     * 获取文件的url
//     * url拼接文件名，可直接访问文件
//     * @param fileName
//     * @return
//     */
//    public String getFileUrl(String fileName) {
//        return domain+"/"+fileName;
//    }
//
//    /**
//     * 清理路径中间的多余的/
//     * @param source
//     * @return
//     */
//    public String clearFilePath(String source) {
//        String element = File.separator;
//        source = StringUtil.trim(source,element);
//        while (source.contains(element+element)) {
//            source = source.replace(element+element,element);
//        }
//        return source;
//    }
//
//    /**
//     * 创建对象
//     */
//    private void getLocation(){
//        System.out.println(uploadPath);
//        this.fileStorageLocation = Paths.get(uploadPath).toAbsolutePath().normalize();
//        try {
//            Files.createDirectories(this.fileStorageLocation);
//        } catch (Exception ex) {
//            throw new FileException("Could not create the directory where the uploaded files will be stored.", ex);
//        }
//    }
}

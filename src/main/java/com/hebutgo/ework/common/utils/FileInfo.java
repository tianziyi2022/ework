package com.hebutgo.ework.common.utils;

import lombok.Data;

/**
 * 上传的文件信息
 * @author zuozhiwei
 */
@Data
public class FileInfo {
    /**
     * 文件名
     * a.pdf
     */
    private String originName;
    /**
     * 存储到文件系统中的文件名
     * a/b/a-1919191919.pdf
     */
    private String storageFileName;
}

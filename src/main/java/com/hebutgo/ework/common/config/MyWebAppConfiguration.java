package com.hebutgo.ework.common.config;

import com.hebutgo.ework.common.CommonConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author tianziyi
 */
@Configuration
public class MyWebAppConfiguration extends WebMvcConfigurerAdapter {
    /**
     * 添加一些虚拟路径的映射
     * 静态资源路径和上传文件的路径
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * @Description: 对文件的路径进行配置, 创建一个虚拟路径/Path/** ，即只要在< img src="/Path/picName.jpg" />便可以直接引用图片
         *这是图片的物理路径  "file:/+本地图片的地址"
         */
        registry.addResourceHandler("/Submit/**").addResourceLocations("file:/"+ CommonConstant.STORE_FOLDER+CommonConstant.SUBMIT_FOLDER);
        registry.addResourceHandler("/Demand/**").addResourceLocations("file:/"+ CommonConstant.STORE_FOLDER+CommonConstant.DEMAND_FOLDER);
        super.addResourceHandlers(registry);
    }
}


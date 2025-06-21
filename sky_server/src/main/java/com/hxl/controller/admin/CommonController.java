package com.hxl.controller.admin;

import com.hxl.constant.MessageConstant;
import com.hxl.result.Result;
import com.hxl.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController("adminCommonController")
@RequestMapping("/admin/common")
@Slf4j
@Api("公共的接口")
public class CommonController {

    /* TODO: 已加入IOC容器 并且自动进行初始化
     *          初始化时: 传入对应的OSS的密钥、列表名等等信息
     */
    @Autowired
    private AliOssUtil aliOssUtil;
    /**
     * 文件上传
     */
    @PostMapping("upload")
    @ApiOperation("文件上传")
    public Result<String> uploadFile(@RequestBody MultipartFile file){
        log.info("文件上传: {}", file);

        String filePath = null;
        try {
            //获取原始文件名
            String originalFilename = file.getOriginalFilename();

            //从原始文件里提取 后缀名
            String tail = originalFilename.substring(originalFilename.lastIndexOf("."));

            //构建文件名
            String fileName = UUID.randomUUID() + tail;

            //构建文件请求路径
            filePath = aliOssUtil.upload(file.getBytes(), fileName);
        } catch (Exception e) {
            //文件上传失败
            log.info(MessageConstant.UPLOAD_FAILED + "{}", e);
        }
        return Result.success(filePath);
    }
}

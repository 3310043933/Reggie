package com.liwenjie.reggie1.controller;

import com.liwenjie.reggie1.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/3/29 22:42
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.file.uploadPath}")
    private String uploadFilePath;

    @Value("${reggie.file.downloadPath}")
    private String downloadFilePath;


    /**
     * 上传文件
     *
     * @param multipartFile
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public R<String> upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        log.info("接收到文件:{}", multipartFile.getOriginalFilename());
        File file = new File(uploadFilePath);
        if (!file.exists()) file.mkdirs();
        // 获得原始文件名与扩展名 生成新的文件名
        String filename = multipartFile.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        String newFileName = UUID.randomUUID() + suffix;
        // 文件转存到目录
        File filePath = new File(this.downloadFilePath +newFileName);
        multipartFile.transferTo(filePath);
        return R.success(newFileName);
    }


    /**
     * 下载文件
     *
     * @param name
     * @param response
     * @throws IOException
     */
    @GetMapping("/download")
    public void download(@RequestParam("name") String name, HttpServletResponse response) throws IOException {
        // 如果文件夹不存在, 创建文件夹
        File file = new File(downloadFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        // 创建输入流
        FileInputStream inputStream = new FileInputStream(downloadFilePath + name);

        // 获得输出流
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] bytes = new byte[1024];
        int read = inputStream.read(bytes);
        // 数据传输
        while (read != -1) {
            outputStream.write(bytes, 0, bytes.length);
            read = inputStream.read(bytes);
            outputStream.flush();
        }
        // 关闭资源
        outputStream.close();
        inputStream.close();
    }

}

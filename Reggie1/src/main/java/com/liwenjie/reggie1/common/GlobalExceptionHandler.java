package com.liwenjie.reggie1.common;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/3/30 15:11
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器
 */
@Slf4j
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody       // 返回结果作为json数据而不是视图
public class GlobalExceptionHandler {

    // 捕获的异常
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> sqlException(SQLIntegrityConstraintViolationException ex) {
        String exMessage = ex.getMessage();
        if (exMessage.contains("Duplicate entry")) {
            log.error("数据库唯一索引异常");
            return R.error(exMessage.split(" ")[2] + "已存在");
        }

        log.error("未知错误:{}", exMessage);
        return R.error("未知错误");
    }

    // 捕获的异常
    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException ex) {
        log.error("未知错误:{}", ex.getMessage());
        return R.error(ex.getMessage());
    }

    @ExceptionHandler(FileNotFoundException.class)
    public R<String> fileException(FileNotFoundException fex) {
        log.error("文件异常,未找到文件:{}", fex.getMessage());
        return R.error("未找到指定文件");
    }

}

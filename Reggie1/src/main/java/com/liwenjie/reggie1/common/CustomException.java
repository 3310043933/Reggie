package com.liwenjie.reggie1.common;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/4/3 23:58
 */

/***
 * 自定义异常
 */
public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }

}

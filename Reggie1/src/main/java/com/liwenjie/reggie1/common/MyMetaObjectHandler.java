package com.liwenjie.reggie1.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/4/3 13:37
 */

/**
 * 元数据处理器, 在插入修改数据时自动设置数值时间等
 */

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充insert:" + metaObject.toString());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        // 通过ThreadLocal获得当前登录用户
        metaObject.setValue("createUser", ThreadLocalContext.getCurrentId());
        metaObject.setValue("updateUser", ThreadLocalContext.getCurrentId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充update:" + metaObject.toString());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", ThreadLocalContext.getCurrentId());
    }
}

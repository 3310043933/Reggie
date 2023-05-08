package com.liwenjie.reggie1.controller;

import com.liwenjie.reggie1.service.DishFlavorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/4/4 21:54
 */
@Slf4j
@RestController
@RequestMapping()
public class DishFlavorController {

    @Autowired
    private DishFlavorService dishFlavorService;
}

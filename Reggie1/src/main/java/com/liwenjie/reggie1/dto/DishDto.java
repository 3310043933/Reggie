package com.liwenjie.reggie1.dto;


import com.liwenjie.reggie1.entity.Dish;
import com.liwenjie.reggie1.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

//    private Integer copies;
}

package com.liwenjie.reggie1.dto;


import com.liwenjie.reggie1.entity.Setmeal;
import com.liwenjie.reggie1.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}

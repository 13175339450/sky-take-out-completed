package com.hxl.vo;

import com.hxl.entity.SetMealDish;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("根据id查询套餐接口的VO")
public class SetMealVO extends SetMealPageVO implements Serializable {

    //TODO: 继承父类的set、get方法 等同于同样继承父类的成员属性

    @ApiModelProperty("套餐菜品关联信息")
    List<SetMealDish> setmealDishes;
}

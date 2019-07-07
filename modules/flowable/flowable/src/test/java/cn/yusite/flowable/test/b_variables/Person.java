package cn.yusite.flowable.test.b_variables;

/**
 * Created by shijie on 2019/3/4
 */

import lombok.Data;

import java.io.Serializable;

@Data
public class Person implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6757393795687480331L;

    private Integer id;//编号

    private String name;//姓名

    private String education;



}

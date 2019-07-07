package cn.yusite.modules.test.entity;

import cn.yusite.common.entity.DataEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "module_test1_demo")
@Getter
@Setter
public class TestData extends DataEntity implements Serializable{


    private String code;

    private Integer age;

    private String phone;

    private Date birthday;
}



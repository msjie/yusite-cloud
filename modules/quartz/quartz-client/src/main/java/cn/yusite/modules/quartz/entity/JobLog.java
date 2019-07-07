package cn.yusite.modules.quartz.entity;

import cn.yusite.common.entity.DataEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "module_quartz_job_log")
@Getter
@Setter
public class JobLog extends DataEntity<JobLog> implements Serializable{


}



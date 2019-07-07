package cn.yusite.modules.quartz.entity;

import cn.yusite.common.entity.DataEntity;
import lombok.Getter;
import lombok.Setter;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.triggers.CronTriggerImpl;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "module_quartz_job")
@Getter
@Setter
public class Job extends DataEntity<Job> implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String STATUS_NORMAL = "0";
    public static final String STATUS_DELETE = "1";
    public static final String STATUS_PAUSED = "2";
    public static final String STATUS_COMPLETE = "3";
    public static final String STATUS_ERROR = "4";
    public static final String STATUS_BLOCKED = "5";
    private String jobName;
    private String jobGroup;
    private String concurrent;
    private String invokeTarget;
    private Integer misfireInstruction;
    private String instanceName;
    private String cronExpression;
    private String description;
    private Date startTime;
    private Date endTime;
    private String remarks;

    @Transient
    @Enumerated(EnumType.STRING)
    private Trigger.TriggerState state;
    @Transient
    private Date prevFireTime;
    @Transient
    private Date nextFireTime;

    public Job convert(Scheduler scheduler, CronTriggerImpl trigger) throws SchedulerException {
        Job a = this;
        this.setJobName(trigger.getName());
        this.setJobGroup(trigger.getGroup());
        this.setDescription(trigger.getDescription());
        this.setInvokeTarget((String) trigger.getJobDataMap().get(""));
        this.setCronExpression(trigger.getCronExpression());
        this.setMisfireInstruction(trigger.getMisfireInstruction());
        this.setConcurrent((String) trigger.getJobDataMap().get(""));
        this.setRemarks((String) trigger.getJobDataMap().get(""));
        this.setState(scheduler.getTriggerState(trigger.getKey()));
        this.setPrevFireTime(trigger.getPreviousFireTime());
        this.setNextFireTime(trigger.getNextFireTime());
        return this;
    }
}



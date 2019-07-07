package cn.yusite.modules.quartz.service;

import cn.yusite.common.service.CrudService;
import cn.yusite.modules.quartz.api.JobLogServiceApi;
import cn.yusite.modules.quartz.api.JobServiceApi;
import cn.yusite.modules.quartz.dao.JobLogRepository;
import cn.yusite.modules.quartz.dao.JobRepository;
import cn.yusite.modules.quartz.entity.Job;
import cn.yusite.modules.quartz.entity.JobLog;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 内部api实现，内部调用
 * Created by shijie on 2019/4/2
 */
@Slf4j
@Service
@RestController
@Transactional(readOnly = true)
public class JobService extends CrudService<JobRepository, Job> implements JobServiceApi {

    @Autowired
    Scheduler scheduler;

    @Override
    public List<Job> findList(Job var1) {
        return null;
    }

    @Override
    public Page<Job> findPage(PageRequest var1, Job var2) {
        return null;
    }

    @Override
    public void delete(Job var1) {
        super.delete(var1);
    }

    @Override
    public Job insert(Job var1) {
        return super.insert(var1);
    }

    @Override
    public Job update(Job var1) {
        return super.update(var1);
    }

    @Override
    public Job get(String id) {
        return super.get(id);
    }

    public void runOnce(Job job) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
        Trigger trigger = scheduler.getTrigger(triggerKey);
        scheduler.triggerJob(trigger.getJobKey());
    }

    public void pause(Job job) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
        Trigger trigger = scheduler.getTrigger(triggerKey);
        scheduler.pauseJob(trigger.getJobKey());
        scheduler.pauseTrigger(trigger.getKey());
    }


}

package cn.yusite.modules.quartz.service;

import cn.yusite.common.service.CrudService;
import cn.yusite.modules.quartz.api.JobServiceApi;
import cn.yusite.modules.quartz.dao.JobRepository;
import cn.yusite.modules.quartz.entity.Job;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 内部api实现，内部调用
 * Created by shijie on 2019/4/2
 */
@Service
public class SchedulerService {

    @Autowired
    Scheduler scheduler;

    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;

    /**
     * 运行一次
     *
     * @param job
     * @throws SchedulerException
     */
    public void runOnce(Job job) throws SchedulerException {

        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
        Trigger trigger = scheduler.getTrigger(triggerKey);
        scheduler.triggerJob(trigger.getJobKey());
    }

    /**
     * 暂停任务
     *
     * @param job
     * @throws SchedulerException
     */
    public void pause(Job job) throws SchedulerException {

        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
        Trigger trigger = scheduler.getTrigger(triggerKey);
        scheduler.pauseJob(trigger.getJobKey());
        scheduler.pauseTrigger(trigger.getKey());
    }

    /**
     * 暂停任务调度
     *
     * @return
     */
    public boolean stopAll() {

        schedulerFactoryBean.stop();
        return true;
    }

    /**
     * 开始任务
     *
     * @param job
     */
    public void resume(Job job) throws SchedulerException {

        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
        Trigger trigger = scheduler.getTrigger(triggerKey);
        scheduler.resumeJob(trigger.getJobKey());
        scheduler.resumeTrigger(trigger.getKey());
    }

    public void save(Job job) {

    }

    public boolean isRunning() throws SchedulerException {

            return !scheduler.isInStandbyMode() && scheduler.isStarted();
    }

    public boolean startAll() {
        try {
            schedulerFactoryBean.setStartupDelay(0);
            schedulerFactoryBean.start();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Job get(Job job) {

        Job a = null;
        try {
            if (StringUtils.isNotBlank(job.getJobName()) && StringUtils.isNotBlank(job.getJobGroup())) {
                TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
                Trigger trigger;
                if ((trigger = scheduler.getTrigger(triggerKey)) instanceof CronTriggerImpl) {
                    a = (new Job()).convert(scheduler, (CronTriggerImpl) trigger);
                }
            }
        } catch (SchedulerException e) {
        }
        return a;
    }

    public void delete(Job job) throws SchedulerException {

        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
        Trigger trigger;
        if ((trigger = scheduler.getTrigger(triggerKey)) != null) {
            scheduler.deleteJob(trigger.getJobKey());
            scheduler.unscheduleJob(trigger.getKey());
        }
    }

    public List<Job> findList(Job job) throws SchedulerException {

        List a = ListUtils.EMPTY_LIST;
        Iterator iterator = scheduler.getTriggerGroupNames().iterator();
        while (iterator.hasNext()) {
            GroupMatcher groupMatcher = GroupMatcher.groupEquals((String) iterator.next());
            Iterator var9 = scheduler.getTriggerKeys(groupMatcher).iterator();
            while (var9.hasNext()) {
                TriggerKey triggerKey = (TriggerKey) var9.next();
                Trigger trigger;
                if ((trigger = scheduler.getTrigger(triggerKey)) instanceof CronTriggerImpl) {
                    a.add((new Job()).convert(scheduler, (CronTriggerImpl) trigger));
                }
            }
        }
        return a;
    }

}
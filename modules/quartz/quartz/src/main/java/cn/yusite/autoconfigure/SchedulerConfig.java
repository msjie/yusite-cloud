package cn.yusite.autoconfigure;

import cn.yusite.modules.quartz.JobDetailListener;
import cn.yusite.modules.quartz.listener.JobSchedulerListener;
import cn.yusite.modules.quartz.listener.JobTriggerListener;
import org.quartz.ListenerManager;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by shijie on 2019/4/17
 */
@Configuration
public class SchedulerConfig {

    @Autowired


    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setAutoStartup(true);
        schedulerFactoryBean.setStartupDelay(5);
        schedulerFactoryBean.setQuartzProperties(quartzProperties());
        return schedulerFactoryBean;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz/quartz.properties"));
        //在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public QuartzInitializerListener executorListener() {
        return new QuartzInitializerListener();
    }

    @Bean
    public Scheduler scheduler() throws IOException, SchedulerException {
        Scheduler scheduler = schedulerFactoryBean().getScheduler();
        ListenerManager listenerManager = scheduler.getListenerManager();
        listenerManager.addJobListener(jobDetailListener());
        listenerManager.addSchedulerListener(jobSchedulerListener());
        listenerManager.addTriggerListener(jobTriggerListener());
        return schedulerFactoryBean().getScheduler();
    }

    @Bean
    public JobDetailListener jobDetailListener(){
        return new JobDetailListener();
    }

    @Bean
    public JobSchedulerListener jobSchedulerListener(){
        return new JobSchedulerListener();
    }

    @Bean
    public JobTriggerListener jobTriggerListener(){
        return new JobTriggerListener();
    }

}

package cn.yusite;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Created by shijie on 2019/4/17
 */
public class TestOne {

    public static Scheduler scheduler(){

        SchedulerFactory sf = null;
        Scheduler sched=null;
        try {
            //sf = new StdSchedulerFactory("quartz/quartz.properties");
            sched = StdSchedulerFactory.getDefaultScheduler();
            //sched = sf.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return sched;
    }

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        SchedulerFactory sf = new StdSchedulerFactory("quartz/quartz.properties");
        Scheduler sched = sf.getScheduler();
        sched.start();
        Thread.sleep(650L*1000L);
        sched.shutdown();
    }
}

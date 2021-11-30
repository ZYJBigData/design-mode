package com.zyj.play.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.time.LocalDateTime;

/**
 * @author yingjiezhang
 */
public class HelloJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Object tv1 = context.getTrigger().getJobDataMap().get("t1");
        Object tv2 = context.getTrigger().getJobDataMap().get("t2");
        Object jv1 = context.getJobDetail().getJobDataMap().get("j1");
        Object jv2 = context.getJobDetail().getJobDataMap().get("j2");
        Object sv = null;
        try {
            sv = context.getScheduler().getContext().get("skey");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        System.out.println(tv1 + ":" + tv2);
        System.out.println(jv1 + ":" + jv2);
        System.out.println(sv);
        System.out.println("hello:" + LocalDateTime.now());

    }

    public static void main(String[] args) throws SchedulerException {
        //scheduler
        Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
        defaultScheduler.getContext().put("skey", "svalue");

        //创建一个Trigger
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                .usingJobData("t1", "tv1").withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(3).repeatForever()).build();
        trigger.getJobDataMap().put("t2", "tv2");

        //创建一个job
        JobDetail job = JobBuilder.newJob(HelloJob.class).usingJobData("j1", "jv1").withIdentity("myJob", "myGroup").build();
        job.getJobDataMap().put("j2", "jv2");

        //注册trigger并启动schduler
        defaultScheduler.scheduleJob(job, trigger);
        defaultScheduler.start();


    }
}

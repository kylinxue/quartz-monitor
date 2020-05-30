package util;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuartzManager {
    public static final Logger LOGGER = LoggerFactory.getLogger(QuartzManager.class);

    public static String JOB_GROUP_NAME = "MY_JOB_GROUP_NAME";
    public static String TRIGGER_GROUP_NAME = "MY_TRIGGER_GROUP_NAME";

    @Autowired
    private Scheduler scheduler;

    /**
     *  增加一个 Job
     * @param jobName
     * @param cls 调度job具体的逻辑
     * @param cron cron表达式
     * @param monitorEntity 具体监控实体
     */
    public void addJob(String jobName, Class cls, String cron, Object monitorEntity) {

        // 创建一个JobDetail实例，与MonitorTask.class绑定
        JobDetail jobDetail = JobBuilder.newJob(cls)
                .withIdentity(jobName, JOB_GROUP_NAME)
                .build();

        // 添加具体监控实体
        jobDetail.getJobDataMap().put("monitorEntity", monitorEntity);

        // 创建一个Trigger实例
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName, TRIGGER_GROUP_NAME)
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (SchedulerException e) {
            LOGGER.info("add job error!");
        }
    }

    /**
     *  移除一个Job
     * @param jobName
     */
    public void removeJob(String jobName) {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME);
        try {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            // 停止并移除触发器
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            LOGGER.info("remove job error!");
        }
    }

}

package util;

import entity.MonitorEntity;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class TimerJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        MonitorEntity monitorEntity = null;
        if (jobDataMap.get("scheduleJob") instanceof MonitorEntity) {
            monitorEntity = (MonitorEntity) jobDataMap.get("scheduleJob");
        }
        if (monitorEntity == null) {
            return;
        }
        // 将 monitorEntity 加入到 monitorQueue 中
        // todo

    }
}

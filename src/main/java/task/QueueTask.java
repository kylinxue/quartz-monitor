package task;

import entity.MonitorEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.QuartzManager;
import util.TimerJob;

@Component
public class QueueTask {
    public static final Logger LOGGER = LoggerFactory.getLogger(QueueTask.class);

    @Autowired
    private QuartzManager quartzManager;

    void addTask(MonitorEntity monitorEntity){
        quartzManager.addJob(monitorEntity.getMonitorId().toString(), TimerJob.class, "* * * * * ? *", monitorEntity);
    }

    void removeTask(String monitorId){
        quartzManager.removeJob(monitorId);
    }

    // todo 根据 周期类型 和 偏移量 生成 cron表达式
    String generateCronExpression(int cycleType, String offset) {

        return "";
    }
}

package util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

@Configuration
public class SchedulerConfiguration implements SchedulingConfigurer {
    @Value("${detect.queue.task.schedulnum:20}")
    private Integer threadNum;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // todo 创建线程池的方式有问题，暂时测试使用
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(threadNum));
    }
}

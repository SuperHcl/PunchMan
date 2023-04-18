package com.umpaytest.mq.delay;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author Hu.ChangLiang
 * @date 2023/4/18 14:44
 */
public class DelayedTask implements Delayed {
    private final String taskName;

    private final long executeTime;

    public DelayedTask(String taskName, long delayTime) {
        this.taskName = taskName;
        this.executeTime = System.currentTimeMillis() + delayTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = executeTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if (o == this) {
            return 0;
        }
        long diff = getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS);
        return Long.compare(diff, 0);
    }

    @Override
    public String toString() {
        return "DelayedTask{" +
                "taskName='" + taskName + '\'' +
                ", executeTime=" + executeTime +
                '}';
    }
}

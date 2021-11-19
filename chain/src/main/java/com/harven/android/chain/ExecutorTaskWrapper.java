package com.harven.android.chain;

import java.util.concurrent.Executor;

/**
 * ExecutorTask
 *
 * @author pc
 * @date 2021/11/12 11:14
 */
public class ExecutorTaskWrapper implements Task {

    private final Executor executor;
    private final Task     task;

    public ExecutorTaskWrapper(Executor executor, Task task) {
        this.executor = executor;
        this.task = task;
    }

    @Override
    public void run(Chain chain) {
        executor.execute(() -> task.run(chain));
    }
}

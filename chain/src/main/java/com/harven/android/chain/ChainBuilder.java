package com.harven.android.chain;

import java.util.concurrent.Executor;

/**
 * ChainBuilder
 *
 * @author pc
 * @date 2021/11/11 19:02
 */
public class ChainBuilder {
    private final ChainImpl chain = new ChainImpl();

    private ChainBuilder() {}

    public static ChainBuilder create(Task task) {
        ChainBuilder builder = new ChainBuilder();
        builder.chain.newTask(task);
        return builder;
    }

    public static ChainBuilder create(Executor executor, Task task) {
        ChainBuilder builder = new ChainBuilder();
        builder.chain.newTask(new ExecutorTaskWrapper(executor, task));
        return builder;
    }

    public ChainBuilder next(Task task) {
        chain.newTask(task);
        return this;
    }

    public ChainBuilder next(Executor executor, Task task) {
        chain.newTask(new ExecutorTaskWrapper(executor, task));
        return this;
    }

    public <P> Controller apply(P param) {
        chain.param = param;
        chain.doNext();
        return chain.getController();
    }
}

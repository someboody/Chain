package com.harven.android.chain;

import com.harven.android.chain.ChainContext;

/**
 * Chain
 *
 * @author pc
 * @date 2021/11/11 19:00
 */
public interface Chain {
    /**
     * 上下文环境,可以存放调用参数和一些其他运行过程中需要向后续任务传递的数据
     *
     * @return {@link ChainContext}对象,里面存放了执行参数和任务执行过程中向其存放的属性参数
     */
    ChainContext context();

    /**
     * 执行下一个{@link Task}<br/>
     * 注意: 如果你想通过{@link Controller#isCompleted()}查询{@link Chain}是否执行完毕,<br/>
     * 或者通过调用{@link Controller#await()}等待{@link Chain}执行完毕<br/>
     * 那么必须在 {@link Task#run(Chain)}方法中必须调用 此方法 或 {@link #complete()} 中的一个<br/>
     * 否则 {@link Controller#isCompleted()} 方法将无法得到正确的结果,并且调用{@link Controller#await()}时将会一直阻塞
     */
    void doNext();

    /**
     * 手动结束 {@link Chain} 链的执行 <br/>
     * 注意: 如果你想通过{@link Controller#isCompleted()}查询{@link Chain}是否执行完毕, <br/>
     * 或者通过调用{@link Controller#await()}等待{@link Chain}执行完毕<br/>
     * 那么必须在 {@link Task#run(Chain)}方法中必须调用 此方法 或 {@link #doNext()} ()} 中的一个<br/>
     * 否则 {@link Controller#isCompleted()} 方法将无法得到正确的结果,并且调用{@link Controller#await()}时将会一直阻塞
     */
    void complete();
}

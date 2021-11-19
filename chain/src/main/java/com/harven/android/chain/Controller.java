package com.harven.android.chain;

/**
 * Controller
 *
 * @author pc
 * @date 2021/11/11 19:17
 */
public interface Controller {
    /** 发送取消的通知 */
    void sendCancel();

    /**
     * 检查链条是否执行完毕<br/>
     *
     * @return {@link Chain}链条中需要执行的任务执行完毕<br/>
     * 或者在主动调用{@link Controller#sendCancel()}取消任务后<br/>
     * (有延迟,需要等到{@link Chain}的下一个任务执行之前去检查该链条是否取消)<br/>
     * 返回 true
     */
    boolean isCompleted();

    /** 等待执行完毕, 如果当前已经执行完毕,则立即返回 */
    void await();
}

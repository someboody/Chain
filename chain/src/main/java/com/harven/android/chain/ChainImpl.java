package com.harven.android.chain;

/**
 * ChainImpl
 *
 * @author pc
 * @date 2021/11/11 19:02
 */
class ChainImpl implements Chain {
    private static final int INIT_CAPACITY = 4;
    private final ChainContext context = new ChainContextImpl(this);
    private final ControllerImpl controller = new ControllerImpl();

    private Task[] nodes = new Task[INIT_CAPACITY];
    private int size;
    private int pointer = -1;
    private static final int MASK_COMPLETED = 0x1;
    private static final int MASK_CANCEL_SIGNAL = 0x10;
    private int signal;

    Object param;

    Controller getController() {
        return controller;
    }

    void newTask(Task task) {
        ensureSize(size + 1);
        nodes[size++] = task;
    }

    private void ensureSize(int newCapacity) {
        if (newCapacity <= nodes.length) return;
        int capacity = nodes.length;
        do {
            capacity += INIT_CAPACITY;
        } while (capacity < newCapacity);
        Task[] newNodes = new Task[capacity];
        System.arraycopy(nodes, 0, newNodes, 0, nodes.length);
        this.nodes = newNodes;
    }

    @Override
    public ChainContext context() {
        return context;
    }

    /**
     * 执行下一个{@link Task}<br/>
     * 注意: 在 {@link Task#run(Chain)}方法中必须调用 此方法 或 {@link #complete()} 中的一个<br/>
     * 否则 {@link Controller#isCompleted()} 方法将无法得到正确的结果,并且{@link Controller#await()}将会一直阻塞
     */
    @Override
    public void doNext() {
        if ((signal & MASK_COMPLETED) != 0) return;
        if ((signal & MASK_CANCEL_SIGNAL) != 0 || ++pointer >= size) {
            controller.setCompleted();
            return;
        }
        nodes[pointer].run(this);
    }

    /**
     * 手动结束 {@link Chain} 链的执行 <br/>
     * 注意: 在 {@link Task#run(Chain)}方法中必须调用 此方法 或 {@link #doNext()} 中的一个<br/>
     * 否则 {@link Controller#isCompleted()} 方法将无法得到正确的结果,并且{@link Controller#await()}将会一直阻塞
     */
    @Override
    public void complete() {
        pointer = nodes.length;
        signal |= MASK_COMPLETED;
        controller.setCompleted();
    }

    class ControllerImpl implements Controller {

        private boolean completed;

        @Override
        public void sendCancel() {
            signal |= MASK_CANCEL_SIGNAL;
        }

        @Override
        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted() {
            this.completed = true;
            synchronized (this) {
                this.notifyAll();
            }
        }

        @Override
        public void await() {
            if (completed) return;
            synchronized (this) {
                while (!completed) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}

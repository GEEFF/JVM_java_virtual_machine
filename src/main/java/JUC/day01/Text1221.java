package JUC.day01;

/**
 * @author ChenY@TJdianzan.com
 * @date 2023/7/29 18:52
 */
public class Text1221 {
    public static void main(String[] args) {
        State state = new State();
        InputThread inputThread = new InputThread(state);
        OutputThread outputThread = new OutputThread(state);
        // 创建两个线程
        Thread in = new Thread(inputThread);
        Thread out = new Thread(outputThread);
        // 启动线程
        in.start();
        out.start();

    }

    //定义锁资源
    static class State{
        public String flag = "车站外";
    }

    // 进站线程
    static class InputThread implements Runnable{

        private State state;

        public InputThread(State state) {
            this.state = state;
        }

        @Override
        public void run() {
            while (true){
                synchronized (state) {
                    if("车站内".equals(state.flag)){
                        // 在车站内 无需进站 释放锁资源
                        try {
                            state.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                    // 仍在在 synchronized中
                    // 不在 车站内 需要进站
                    System.out.println("进站12！");
                    // 进站完了 之后 修改锁资源锁处的状态
                    state.flag = "车站内";
                    // 唤醒其他线程
                    state.notify();
                }

            }

        }
    }

    // 出站线程
    static class OutputThread implements Runnable{

        private State state;

        public OutputThread(State state) {
            this.state = state;
        }

        @Override
        public void run() {
            while (true){
                synchronized (state) {
                    if ("车站外".equals(state.flag)){
                            // 在车站外 不需要等待 直接出站 释放锁
                        try {
                            state.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                    // 不在车站外 需要出站
                    System.out.println("出站21！");
                    // 修改锁状态
                    state.flag = "车站外";
                    // 唤醒其他线程
                    state.notify();
                }

                }
            }

        }
    }


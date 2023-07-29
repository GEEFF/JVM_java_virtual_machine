package JUC.day01;

import java.io.OutputStream;

/**
 * @author ChenY@TJdianzan.com
 * @date 2023/7/29 17:08
 */
public class WaitAndNotify {

    // main 方法
    public static void main(String[] args) {
        State state = new State();
        InputThread inputThread = new InputThread(state);
        OutputThread outputThread = new OutputThread(state);
        // 定义两个线程
        Thread in = new Thread(inputThread);
        Thread out =  new Thread(outputThread);
        // 启动线程
        in.start();
        out.start();
    }


    //控制状态
    static class State{
        // 状态标识
        public String flag = "车站外";
    }



    //出站线程
    static class OutputThread implements Runnable{
        private State state;

        public OutputThread(State state) {
            this.state = state;
        }


        @Override
        public void run() {
            while(true){
                synchronized (state){
                    if ("车站外".equals(state.flag)){
                        try {
                            // 如果是在车站外 就不用出站 等待 释放锁
                            state.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    // 不再车站内 你就要出来
                    System.out.println("出站！");
                    // 将锁资源修改成车站外
                    state.flag = "车站外";
                    // 唤醒等待的线程
                    state.notify();
                }
            }

        }
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
                synchronized (state){
                    if ("车站内".equals(state.flag)){
                        try {
                            // 如果在车站内 就不用进站 等待 释放锁
                            state.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("进站!");
                    state.flag = "车站内";
                    // 唤醒state等待的线程
                    state.notify();
                }
            }

        }
    }
}

package cn.xdl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlackQueue {
    private int count = 0;
    private BlockingQueue<Integer> queue =new ArrayBlockingQueue<>(10);

    public static void main(String[] args) {
        BlackQueue b = new BlackQueue();
        for (int i =1;i<=5;i++){
            new Thread(b.new Pusher(),"生产者- "+i).start();
            new Thread(b.new Poper(),"消费者- "+i).start();
        }
    }
    class Pusher implements Runnable{

        @Override
        public void run() {
            for (int i =0;i<10;i++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    //将指定元素插入此队列.将等待可用的空间
                    queue.put(i);
                    count++;
                    System.out.println("生产者 "+Thread.currentThread().getName()+"总共有"+count+"个资源");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class Poper implements  Runnable{

        @Override
        public void run() {
            for (int i=0;i<10;i++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    //获取并移除队列的头部,在元素变得可用之前一直等待
                    queue.take();
                    count--;
                    System.out.println("消费者 "+Thread.currentThread().getName()+"一共有"+count+"个资源");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

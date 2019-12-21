package cn.xdl;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockThread {
    //创建锁对象
    static ReentrantLock lock  = new ReentrantLock();
    //创建Condition实例与锁对象关联
    static Condition pushCondition = lock.newCondition();
    static Condition popCondition  = lock.newCondition();

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        //创建一个最大容量为10的线程池
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i =0;i<5;i++){
            service.submit(new push(list,5,lock));
        }
        for (int i =0;i<5;i++){
            service.submit(new pop(list,lock));
        }
    }
    static class push implements Runnable{
        private List<Integer> list;
        private int max;
        private Lock lock;
        public push(List list,int max,Lock lock){
            this.list=list;
            this.max=max;
            this.lock=lock;
        }
        @Override
        public void run() {
            while (true){
                lock.lock();
                try {
                    while (list.size()==max){
                        System.out.println("生产者"+Thread.currentThread().getName()+"容量已满,开始等待!");
                        pushCondition.await();
                        System.out.println("生产者"+Thread.currentThread().getName()+"退出等待");
                    }
                    Random r= new Random();
                    int i = r.nextInt(100);
                    System.out.println("生产者"+Thread.currentThread().getName()+"生产数据"+i);
                    list.add(i);
                    //唤醒消费者
                    popCondition.signal();
                    Thread.sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        }
    }
    static class pop implements  Runnable{
        private List<Integer> list;
        private Lock lock;
        public pop(List list,Lock lock){
            this.list=list;
            this.lock=lock;
        }

        @Override
        public void run() {
            while (true){
                lock.lock();
                try{
                    while (list.isEmpty()){
                        System.out.println("消费者"+Thread.currentThread().getName()+"容器已空,开始等待");
                        popCondition.await();
                        System.out.println("消费者"+Thread.currentThread().getName()+"退出等待");
                    }
                    Integer integer = list.remove(0);
                    System.out.println("消费者"+Thread.currentThread().getName()+"消费了"+integer);
                    pushCondition.signal();
                    Thread.sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }

            }
        }
    }

}

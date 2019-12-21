package cn.xdl;

import java.util.LinkedList;
import java.util.Queue;

public class Repositroy {
    private Queue<Integer> data =new LinkedList<>();
    private int count =0;
    private int max = 10;

    /**
     * 将产品放入仓库的方法
     * @param n 产品n
     */
    public void push(int n){
        synchronized (this){
            while (count==max){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            data.offer(n);
            count++;
            System.out.println(Thread.currentThread().getName()+" 生产者生产,"+"仓库目前还有"+count+"件商品");
            notifyAll();
        }
    }
    public  int pop(){
        synchronized (this) {
            while (count==0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            count--;
            notifyAll();
            System.out.println(Thread.currentThread().getName()+"消费者消费,目前总共有"+count);
        }
        return data.poll();
    }
}

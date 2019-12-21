package cn.xdl;

public class Main {

    public static void main(String[] args) {
        Repositroy re = new Repositroy();
        Pusher  p =new Pusher(re);
        Poper po = new Poper(re);
        Thread t1  =new Thread(p);
        Thread t2 = new Thread(p);
        Thread t3 = new Thread(p);
        Thread t4 =new Thread(po);
        Thread t5 =new Thread(po);
        Thread t6 =new Thread(po);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
    }

}

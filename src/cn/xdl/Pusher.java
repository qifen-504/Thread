package cn.xdl;

import java.util.Random;

public class Pusher implements  Runnable{
    private Repositroy  re;
    Pusher(Repositroy re){
        this.re=re;
    }

    @Override
    public void run() {
        int temp = 0;
        for (int i =0;i<10;i++){
            Random rr= new Random();
            temp =rr.nextInt(100);
            re.push(temp);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

package cn.xdl;

public class Poper implements Runnable {
    private Repositroy re;
    Poper(Repositroy re){
        this.re=re;
    }

    @Override
    public void run() {
        for (int i =0;i<10;i++){
            int temp =re.pop();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

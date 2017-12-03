package demo2;


class  Runner implements Runnable {
    @Override
    public void run() {
        for (int i =0; i < 10; i++ ) {
            System.out.println("Ehllo " + i);

//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
}
public class App2 {

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runner());

        Thread t2 = new Thread(new Runner());

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

